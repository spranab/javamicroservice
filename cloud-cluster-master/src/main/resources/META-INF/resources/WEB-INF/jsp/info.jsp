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
<script type="text/javascript" src="/cluster/js/bootstrap.min.js"></script>
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
	background: #E9F5EE;
}

.oddRowOriginal {
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

.tableHeader {
	background: #000;
	color: #ffffff;
	font-weight: bold;
}

th {
	text-align: center;
}

a {
	text-decoration: none;
}

.panel-heading a:after {
	font-family: 'Glyphicons Halflings';
	content: "\e114";
	float: right;
	color: grey;
}

.panel-heading a.collapsed:after {
	content: "\e080";
}
</style>
</head>
<body>
	<div id="wrapper-container">
		<div id="header">Cloud Cluster Master</div>
		<div class="container-fluid">

			<div class="row">
				<h1>Cluster Information</h1>
				<div class="col-xs-6">
					<table class="dataTable">
						<tr class="oddRowOriginal">
							<td><b>Cluster Name</b></td>
							<td>${master.masterName}</td>
						</tr>
					</table>
				</div>
				<div class="col-xs-6">
					<table class="dataTable">
						<tr class="oddRowOriginal">
							<td><b>Up Time</b></td>
							<td>${uptime}</td>
						</tr>
					</table>
				</div>
			</div>



			<c:forEach items="${master.slaveMap}" var="slaveEntry">
				<c:set var="slave" value="${slaveEntry.value}"></c:set>
				<div class="row">
					<div class="panel-group" id="accordion" style="margin: 20px;">
						<div class="panel panel-default" id="panel1">
							<div class="panel-heading">

								<h4 class="panel-title">
									<c:choose>
										<c:when test="${slave.online} == 'false'">
											<span class="glyphicon glyphicon-remove-circle"></span>
										</c:when>
										<c:otherwise>
											<span class="glyphicon glyphicon-ok-circle"></span>
										</c:otherwise>
									</c:choose>
									<a data-toggle="collapse" data-target="#collapse_${slaveEntry.key}"
										href="#collapse_${slaveEntry.key}">${slave.clusterName} -
										${slave.hostAddress} </a>
								</h4>

							</div>
							<div id="collapse_${slaveEntry.key}" class="panel-collapse collapse in">
								<div class="panel-body">
									<div class="row">
										<h1>Cluster Information</h1>
										<div class="col-xs-6">
											<table class="dataTable">
												<tr class="oddRow">
													<td><b>Cluster ID</b></td>
													<td>${slave.slaveId}</td>
												</tr>
												<tr>
													<td><b>Cluster Name</b></td>
													<td>${slave.clusterName}</td>
												</tr>
											</table>
										</div>
										<div class="col-xs-6">
											<table class="dataTable">
												<tr class="oddRow">
													<td><b>Address</b></td>
													<td>${slave.hostAddress}</td>
												</tr>
												<tr>
													<td><b>Up Time</b></td>
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
														<td><b>Microservice ID</b></td>
														<td>${microservice.value.serviceId}</td>
													</tr>
													<tr>
														<td><b>Service Name</b></td>
														<td>${microservice.value.serviceName}</td>
													</tr>
													<tr class="oddRow">
														<td><b>Address</b></td>
														<td>${microservice.value.serviceAddress}</td>
													</tr>
													<tr>
														<td><b>Full Path</b></td>
														<td>${microservice.value.fullPath}</td>
													</tr>
													<tr class="oddRow">
														<td><b>Executable JAR</b></td>
														<td>${microservice.value.jarFileName}</td>
													</tr>
													<tr>
														<td><b>Instances</b><br> <a
															href="/microservice/create/${slave.slaveId}/${microservice.value.serviceId}">Create
																New Instance</a></td>
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
																					<a
																						href="/microservice/stop/${slave.slaveId}/${microservice.value.serviceId}/${instance.value.instanceId}">Stop</a>
																				</c:when>
																				<c:otherwise>
																					<a
																						href="/microservice/start/${slave.slaveId}/${microservice.value.serviceId}/${instance.value.instanceId}">Start</a>
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
						</div>
					</div>

				</div>

			</c:forEach>


		</div>

	</div>
</body>
</html>