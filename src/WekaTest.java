import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.trees.*;

import java.io.ByteArrayInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Properties;


public class WekaTest {
	
	private J48 tree = new J48();
	private Hashtable<String,String> dictionary = new Hashtable<String,String>();
	final private String quandlAPIKey = "Vy4f3PMsyBZXpVCNdtvK";
	private String arffMeta = "";
	
	public WekaTest(){
		tree.setUnpruned(false);
		dictionary.put("AEROSPACE","NASDAQOMX/NQUSB2713");
		dictionary.put("AUTOMOBILE","NASDAQOMX/QAUTO");
		dictionary.put("CONSUMER_ELECTRONICS","NASDAQOMX/NQUSB3743"); 
		dictionary.put("CONSUMER_FINANCE","NASDAQOMX/NQUSB8773"); 
		dictionary.put(" DEFENSE","NASDAQOMX/NQUSB2717"); 
		dictionary.put("ENERGY","NASDAQOMX/SVO"); 
		dictionary.put("FINANCE","NASDAQOMX/OFIN"); 
		dictionary.put("GRNSOLAR","NASDAQOMX/GRNSOLAR"); 
		dictionary.put("HEALTH_CARE","NASDAQOMX/IXHC"); 
		dictionary.put("INDUSTRY","NASDAQOMX/INDS"); 
		dictionary.put("INSTURACE","NASDAQOMX/INSR"); 
		dictionary.put("OIL","NASDAQOMX/OSX"); 
		dictionary.put("SEMICONDUCTORS","NASDAQOMX/SOX"); 
		dictionary.put("SMARTPHONES","NASDAQOMX/QFON"); 
		dictionary.put("SOFTWARE","NASDAQOMX/NQUSB9537"); 
		dictionary.put("TELECOMMUNICATIONS","NASDAQOMX/IXTC"); 
		dictionary.put("TRANSPORTATION","NASDAQOMX/TRAN"); 
	}
		
	/**
	 * This method reads from Profile.txt to load
	 * the list of tags of the given stock ticker.
	 * @param    ticker   the stock ticker of a tech company
	 * @return            the tags associated with the company
	 */
	public String [] getProfile(String ticker){		
		try {
			// Read in the lines of the file
			FileReader file = new FileReader("C:/Users/Rishabh Upadhyay/Downloads/Big Data/profile.txt");
			BufferedReader bufRed = new BufferedReader(file);
			String line = bufRed.readLine();
			String [] tokens = new String[1];
			
			// Find the line that contains the given ticker
			while( line != null && !(tokens=line.split(","))[0].equals(ticker)){
				line = bufRed.readLine();
			}
			
			// If the ticker was not in the file
			if(line == null){
				bufRed.close();
				return null;
			}
			bufRed.close();
			return tokens;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new String[1];
		}
	
	}
	
	
	/**
	 * This method uses the stock ticker and tags
	 * to find and select from the table in the database
	 * that contains the data for the company
	 * @param    profile   the ticker and tags
	 * @return             string representation of
	 *                     the data as an arff file
	 */
	public String getData( String [] profile ){
		
		try{
			
			// Set up the properties of the connection to the database
			Class.forName("com.mysql.jdbc.Driver");
			Properties connectionProps = new Properties();
			connectionProps.put("user", "root");
			connectionProps.put("password", "neha0411");
			String dbURL = "jdbc:mysql://localhost/big_data";
			
			// Connect to the database
			Connection conn = DriverManager.getConnection(dbURL,connectionProps);
			PreparedStatement state = conn.prepareStatement("select * from big_data."+profile[0]);
			
			// Retrieve company data 
			ResultSet rs = state.executeQuery();
			
			//Build arff metadata
			String content = "@relation " + profile[0] + " \n";
			for( int attr = 1; attr < profile.length; attr++ ) {
				content += "@attribute " + profile[attr] + " {True, False} \n";
			}
			content += "@attribute class {True, False} \n @data \n";

			// Save the arff meta data for later use
			this.arffMeta = content;
			
			// So long as there are more rows to read
			while(rs.next()){
				for( int tag = 1; tag < profile.length; tag++ ) {
					content += rs.getString(profile[tag]) + ", ";
				}
				
				if( rs.isLast() ){
					content += rs.getString("Rise");
				}
				else{
					content += rs.getString("Rise") + "\n";
				}
			}
			return content;
		}
		catch(Exception e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * This method returns the current rise
	 * or fall values for each tag associated
	 * with the company.
	 * @param    profile   the ticker and tags
	 * @return             a record to be classified,
	 *                     wrapped inside of an Instances object
	 */
	public Instances getNewData( String [] profile ){
		try{
			
			// For every tag, determine whether the current 
			// value is a rise(True) or fall(False)
			String values = "";
			for( int value = 0; value < profile.length-1; value++ ){
				values += getNewValue(profile[value+1]) + ", ";
			}
			
			// ? is used to notify Weka that this 
			// is a record to be classified. It replaces
			// the rise value found in records from the database.
			values += " ? \n";
			
			// Add the arff metadata
			values = this.arffMeta + values;
			
			// Use the arff data to initialize a DataSource object
			DataSource data = new DataSource( new ByteArrayInputStream(values.getBytes()));
			return data.getDataSet();
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	/**
	 * This method makes a call to Quandl to determine
	 * whether the price of the given index is a rise or fall.
	 * @param    index   the name of the index whose value
	 *                   we want to calculate
	 * @return           True for rise, False for fall
	 */
	public String getNewValue( String index ){
		
		// A GreagorianCaledar object to get the current date
		Calendar calendar = new GregorianCalendar();
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH)+1;
		int year = calendar.get(Calendar.YEAR);
		
		// Format as YYYY-MM-DD
		// Today's date
		String today = "" + year + "-"  + ( month<10 ? "0" + month : month ) +
				       "-" + ( day < 10 ? "0" + day : day);
		
		// The date of five days ago
		if( day - 5 <= 0 ){
			month--;
			day=20;
		}
		else{
			day -= 5;
		}
		String fiveDaysBefore = "" + year + "-"  + ( month<10 ? "0" + month : month ) +
			       "-" + ( day < 10 ? "0" + day : day);
		
		//Connect to Quandl
		try {
			
			// Set the driver
			Class.forName("quandl.jdbc.QuandlDriver");
			
			// Specify which table
			String table = dictionary.get(index.toUpperCase());
			
			// Set the connection url
			String url = "jdbc:quandl://www.quandl.com/api/v1?tables=" + table;
			
			// Connect to Quandl's database
			Connection conn = DriverManager.getConnection(url,"",quandlAPIKey);

			// Set the query
			String query = "SELECT * from \"" + table +
					       "\" WHERE (\"Trade Date\" <= '" + today + "') AND (\"Trade Date\" >= '" + fiveDaysBefore +
					       "') ORDER BY \"Trade Date\";";
			
			// Execute the query
			Statement state = conn.createStatement();
			ResultSet rst = state.executeQuery(query); 
			
			// Calculate whether the latest record is a rise or fall.
			double latest = 0.0;
			double old = 0.0;
			while(rst.next()){
				old = latest;
				latest = rst.getDouble("High");
			}

			return latest - old > 0 ? "True" : "False";
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * This method determines whether the value
	 * of the given stock will rise or fall.
	 * @param stock
	 * @return
	 */
	public String connectionTest(String stock){
			
		try{
			String ticker = stock.toUpperCase();
			
			// Get company profile
			String [] profile = getProfile(ticker);	
			if(profile == null){
				return "ERROR";
			}
			
			// Get company data
			String content = getData( profile );
			
			// Create a data source object
			DataSource data = new DataSource( new ByteArrayInputStream(content.getBytes()));
			
			// Get the instances
			Instances instance = data.getDataSet();
			
			// Set target 
			instance.setClassIndex(instance.numAttributes() - 1);
			
			// Build tree
			tree.buildClassifier(instance);
			
			// Get data to be classified
			Instances currentData = getNewData(profile);
			
			// Set target
			currentData.setClassIndex(instance.numAttributes() - 1);
			Instance prediction = currentData.firstInstance();
			
			// Classify
			double classification = tree.classifyInstance(prediction);
			prediction.setClassValue(classification);
						
			return "" + prediction.stringValue(prediction.classIndex());
			
			
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	 
	public static void main( String [] args) {
				
		WekaTest test = new WekaTest();		
	} 
	
}
