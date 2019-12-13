<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@include file="/template/header.jsp" %>

<script src="https://code.jquery.com/jquery-1.12.4.min.js"></script>

<script type="text/javascript">
$(document).ready(function(){
    $("#checkit").click(function(){
        if($(this).is(':checked')){
        var shippingStreet = $("#shippingStreet").val();
        var shippingApartmentNumber = $("#shippingApartmentNumber").val();
        var shippingCity = $("#shippingCity").val();
        var shippingState = $("#shippingState").val();
        var shippingCountry = $("#shippingCountry").val();
        var shippingZip = $("#shippingZip").val();
        
        //set the variable in lower form with vars set above
        $("#shippingStreetb").val(shippingStreet);
        $("#shippingApartmentNumberb").val(shippingApartmentNumber); 
        $("#shippingCityb").val(shippingCity);
        $("#shippingStateb").val(shippingState);
        $("#shippingCountryb").val(shippingCountry);
        $("#shippingZipb").val(shippingZip);
        
        
        }else{
//uncheck - clear input
        $("#Address1b").val("");
        $("#Address2b").val(""); 
        $("#Cityb").val("");
        $("#Countryb").val("");
        }
    });
});

</script>

<div class="container-wrapper">
    <div class="container">
        <div class="page-header">
            <h1>Register Customer</h1>

            <p class="lead">Please fill in your information below:</p>
        </div>

        
		<form:form action="/registercustomer" method="post"
                   modelAttribute="billingAddress" >
        <h3>Basic Info</h3>

        <div class="form-group">
            <label for="name">Name</label><form:errors path="customer.customerName" cssStyle="color: #ff0000"/>
            <form:input path="customer.customerName" id="name" class="form-Control"/>
        </div>

       <div class="form-group">
            <label for="email">Email</label><span style="color: #ff0000">${emailMsg}</span><form:errors
                path="customer.customerEmail" cssStyle="color: #ff0000"/>
            <form:input path="customer.customerEmail" id="email" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="phone">Phone</label>
            <form:input path="customer.customerPhone" id="phone" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="username">Username</label><span style="color: #ff0000">${usernameMsg}</span><form:errors
                path="customer.username" cssStyle="color: #ff0000"/>
            <form:input path="customer.username" id="username" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="password">Password</label><form:errors path="customer.password" cssStyle="color: #ff0000"/>
            <form:password path="customer.password" id="password" class="form-Control"/>
        </div>

	
	 	
			
		<div class = "shipping"  >		
		 <h3>Shipping Address</h3>
		
        <div class="form-group">
            <label for="shippingStreet">Street Name</label>
            <form:input path="shippingAddress.streetName" id="shippingStreet" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingApartmentNumber">Apartment Number</label>
            <form:input path="shippingAddress.apartmentNumber" id="shippingApartmentNumber" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCity">City</label>
            <form:input path="shippingAddress.city" id="shippingCity" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingState">State</label>
            <form:input path="shippingAddress.state" id="shippingState" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCountry">Country</label>
            <form:input path="shippingAddress.country" id="shippingCountry" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingZip">Zipcode</label>
            <form:input path="shippingAddress.zipCode" id="shippingZip" class="form-Control"/>
        </div>
        
         </div>
         
          <input type="checkbox" name="same" id = "checkit" value="same" >Same As shipping Address<br>
         	
          	<div class="billing">
       		 <h3>Billing Address</h3>

        <div class="form-group">
            <label for="shippingStreet">Street Name</label>
            <form:input path="streetName" id="shippingStreetb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingApartmentNumber">Apartment Number</label>
            <form:input path="apartmentNumber" id="shippingApartmentNumberb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCity">City</label>
            <form:input path="city" id="shippingCityb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingState">State</label>
            <form:input path="state" id="shippingStateb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingCountry">Country</label>
            <form:input path="country" id="shippingCountryb" class="form-Control"/>
        </div>

        <div class="form-group">
            <label for="shippingZip">Zipcode</label>
            <form:input path="zipCode" id="shippingZipb" class="form-Control"/>
        </div>
        
        	 <input type="submit" value="submit" class="btn btn-default">
        <a href="<c:url value="/" />" class="btn btn-default">Cancel</a>

			</div>

        <br><br>
       	
       	</form:form>	



        <%@include file="/template/footer.jsp" %>
        
  </div></div>
        
