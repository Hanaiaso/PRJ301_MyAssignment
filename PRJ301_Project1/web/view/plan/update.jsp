<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Update Plan</title>
    </head>
    <body>
        <h2>Update Plan</h2>
        <form action="update" method="post">
            <input type="hidden" name="plid" value="${plan.id}" />
            <label for="plname">Plan Name:</label>
            <input type="text" id="plname" name="plname" value="${plan.name}" required /><br/>

            <label for="StartDate">Start Date:</label>
            <input type="date" id="StartDate" name="StartDate" value="${plan.start}" required /><br/>

            <label for="EndDate">End Date:</label>
            <input type="date" id="EndDate" name="EndDate" value="${plan.end}" required /><br/>

            <label for="did">Department:</label>
            <select name="did" id="did" required>
                <c:forEach items="${requestScope.depts}" var="d">
                    <option value="${d.id}" 
                            ${plan.dept.id == d.id ? "selected" : ""}>${d.name}</option>
                </c:forEach>
            </select> <br/>

            <h3>Select Campaigns:</h3>
            <c:forEach var="campaign" items="${campains}">
                <input type="checkbox" name="campaignIds" value="${campaign.id}" 
                       <c:if test="${fn:contains(plan.campains, campaign.id)}">checked</c:if> />
                <label>Campaign ID: ${campaign.id}</label><br/>

                <label for="quantity_${campaign.id}">Quantity:</label>
                <input type="number" id="quantity_${campaign.id}" name="quantity_${campaign.id}" 
                       value="${fn:contains(plan.campains, campaign.id) ? campaign.quantity : 0}" 
                       min="0" required /><br/>

                <label for="cost_${campaign.id}">Cost:</label>
                <input type="number" id="cost_${campaign.id}" name="cost_${campaign.id}" 
                       value="${fn:contains(plan.campains, campaign.id) ? campaign.cost : 0}" 
                       min="0" step="0.01" required /><br/><br/>
            </c:forEach>

            <input type="submit" value="Update" />
        </form>
    </body>
</html>
