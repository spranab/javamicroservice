<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="/cluster/css/bootstrap.min.css" />
<title>Cluster Information</title>
<script type="text/javascript" src="/cluster/js/jquery-3.1.0.min.js"></script>
<style>
body {
	background: #E9F5EE;
}

h1 {
	font-size: 25px;
	line-height: 30px;
	padding-left: 10px;
	font-weight: bold;
}

h4 {
	padding-left: 15px;
	font-weight: bold;
}

#header {
	background: #000;
	color: #ffffff;
	height: 60px;
	font-size: 30px;
	font-weight: bold;
	vertical-align: middle;
	text-align: center;
}

.oddRow {
	background: #ffffff;
}

.dataTable {
	width: 100%;
	border-collapse: collapse;
}

.dataTable td {
	border-top: #000 1px solid;
	padding-left: 10px;
}

.bordered {
	border: #000 1px solid;
}

.tableHeader{
	background: #000;
	color: #ffffff;
	font-weight: bold;
}

th{
	text-align: center;
}

a {
	text-decoration: none;
}
</style>
</head>
<body>
	<div id="wrapper-container">
		<div id="header">Cloud Cluster Slave</div>
		<div class="container-fluid">
			<div class="row">
				<h1>Cluster Information</h1>
				<div class="col-xs-6">
					<table class="dataTable">
						<tr class="oddRow">
							<td>Cluster ID</td>
							<td>${slave.slaveId}</td>
						</tr>
						<tr>
							<td>Cluster Name</td>
							<td>${slave.clusterName}</td>
						</tr>
					</table>
				</div>
				<div class="col-xs-6">
					<table class="dataTable">
						<tr class="oddRow">
							<td>Address</td>
							<td>${slave.hostAddress}</td>
						</tr>
						<tr>
							<td>Up Time</td>
							<td>${uptime}</td>
						</tr>
					</table>
				</div>
			</div>
			<div class="row">
				<h1>Registered Service</h1>
				<c:forEach items="${slave.microserviceMap}" var="microservice">
					<div class="col-xs-6">
						<h4>
							<i>${microservice.value.serviceName}</i>
						</h4>
						<table class="dataTable bordered">
							<tr class="oddRow">
								<td>Microservice ID</td>
								<td>${microservice.value.serviceId}</td>
							</tr>
							<tr>
								<td>Service Name</td>
								<td>${microservice.value.serviceName}</td>
							</tr>
							<tr class="oddRow">
								<td>Address</td>
								<td>${microservice.value.serviceAddress}</td>
							</tr>
							<tr>
								<td>Full Path</td>
								<td>${microservice.value.fullPath}</td>
							</tr>
							<tr class="oddRow">
								<td>Executable JAR</td>
								<td>${microservice.value.jarFileName}</td>
							</tr>
							<tr>
								<td><b>Instances</b><br><a href="/microservice/create/${microservice.value.serviceId}">Create New Instance</a></td>
								<td>
									<table class="dataTable">
										<tr class="tableHeader">
											<th>ID</th>
											<th>Port</th>
											<th>Online?</th>
											<th>Action</th>
										</tr>
										<c:set var="counter" value="0"></c:set>
										<c:forEach items="${microservice.value.instances}"
											var="instance">
											<c:choose>
												<c:when test="${counter mod 2 == 0}">
													<c:set var="instanceStyle" value=""></c:set>
												</c:when>
												<c:otherwise>
													<c:set var="instanceStyle" value="class=\"oddRow\""></c:set>
												</c:otherwise>
											</c:choose>
											<tr ${instanceStyle} style="text-align: center;">
												<td>${instance.value.instanceId}</td>
												<td>${instance.value.port}</td>
												<td>${instance.value.online}</td>
												<td><c:choose>
														<c:when test="${instance.value.online == 'true'}">
															<a href="/microservice/stop/${microservice.value.serviceId}/${instance.value.instanceId}">Stop</a>
														</c:when>
														<c:otherwise>
															<a href="/microservice/start/${microservice.value.serviceId}/${instance.value.instanceId}">Start</a>
														</c:otherwise>
													</c:choose></td>
											</tr>
											<c:set var="counter" value="${counter+1}"></c:set>
										</c:forEach>
									</table>
								</td>
							</tr>
						</table>
					</div>
				</c:forEach>
			</div>
		</div>

	</div>
</body>
</html>