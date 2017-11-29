<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Prices Prediction</title>
<style>
body{
   
    background-image: url(s4.jpg);
    background-size: cover;
    background-repeat: no-repeat;
	border-image-outset: 2px solid darkgray; 
    }
h1{
    background-color: #ffd39b;
    color: red;
    padding: 5px;
    }
.button
    {
	color:red;
	background-color: yellow;
	font-size: 20px;
	border: 2px solid blue;
	border-radius: 500px;
    }
	select.stock_symbol{
    width:30%;
	text-align: center
	size : "10"
}
</style>
    </head>
    <body>
    	<center>
			<h1>Stock Prices Prediction</h1>
        <h2>Choose the Stock Symbol</h2>
        <form action="output.jsp" method="post"> 
               <br/>
       	 <select class = "stock_symbol" name="stock_symbol">
                        <option>AAPL</option>
						<option>ADBE</option>
						<option>ADI</option>
						<option>ADP</option>
						<option>ADSK</option>
						<option>AMAT</option>
						<option>AMD</option>
						<option>CA</option>
						<option>CERN</option>
						<option>CRM</option>
						<option>CSC</option>
						<option>CSCO</option>
						<option>CTSH</option>
						<option>CTXS</option>
						<option>CVG</option>
						<option>DOV</option>
						<option>ETN</option>
						<option>FISV</option>
						<option>FSLR</option>
						<option>GOOG</option>
						<option>HPQ</option>
						<option>IBM</option>
						<option>INTC</option>
						<option>INTU</option>
						<option>IPG</option>
						<option>ITW</option>
						<option>JBL</option>
						<option>JNPR</option>
						<option>LLL</option>
						<option>LLTC</option>
						<option>MCHP</option>
						<option>MSFT</option>
						<option>MU</option>
						<option>NTAP</option>
						<option>NVDA</option>
						<option>OMC</option>
						<option>ORCL</option>
						<option>QCOM</option>
						<option>RHI</option>
						<option>RHT</option>
						<option>SYMC</option>
						<option>TDC</option>
						<option>TXN</option>
						<option>VRSN</option>
						<option>WDC</option>
						<option>XLNX</option>
						<option>YHOO</option>
                    </select>
			   <br/>
			  <br/>
         <input type="submit" value="Result" button class="button" name="button" type="button"></button> <br><br/>
        </form>
        </center>
    </body>
</html>
