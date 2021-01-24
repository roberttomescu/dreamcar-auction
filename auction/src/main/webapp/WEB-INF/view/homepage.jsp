<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head></head>

<body>

	<security:authorize access="hasRole('ROLE_USER')">
      <div id="navigation"></div>
    </security:authorize>

    <h1>This is the body of the sample view</h1>
    

    <security:authorize access="hasRole('ROLE_ADMIN')">
      <div id="add-auction-form"></div>
    </security:authorize>
	
    <security:authorize access="hasRole('ROLE_USER')">
		<div id="all-auctions"></div>
    </security:authorize>
    
    <a href="<c:url value="/perform_logout" />">Logout</a>
    
    
    <script src="https://unpkg.com/react@16.4.1/umd/react.production.min.js"></script>
	<script src="https://unpkg.com/react-dom@16.4.1/umd/react-dom.production.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.21.1/axios.js"></script>
	
    <script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
    
    <script src="AddAuctionForm.js" type="text/babel"></script>
    <script src="Auctions.js" type="text/babel"></script>

</body>