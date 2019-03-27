<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="${contextPath}/assets/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${contextPath}/assets/css/font-awesome.css" rel="stylesheet" media="screen">
<link href="${contextPath}/assets/css/main.css" rel="stylesheet" media="screen">
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="${contextPath}/list-computers"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
        	<div class="row">
        		<div class="col-lg-5 col-md-12">
	        		<h1 id="homeTitle"> ${pagination.getItemCount()} Computer(s) found.</h1>
        		</div>
        		<div class="col-lg-7 col-md-12">
        			<c:if test='${feedback.getMessage() != null}'>
        				<p class="alert alert-${feedback.getStatus()}">${feedback.getMessage()}</p>
        			</c:if>
        		</div>
        	</div>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="${contextPath}/add-computer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="${contextPath}/delete-computers" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${computers}" var="computer">
					    <tr>      
					        <td class="editMode">
					       		<input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
					        </td>
					        <td>${computer.getName()}</td>
					        <td>${computer.getIntroduction()}</td>
					        <td>${computer.getDiscontinuation()}</td>
					        <td>${computer.getCompany()}</td>  
					    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                    <a href="${contextPath}/list-computers?page=${pagination.previous()}&itemPerPage=${pagination.getItemPerPage()}" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
              <li>
              	<a class="${pagination.compareCurrent(1)}" href="${contextPath}/list-computers?page=1&itemPerPage=${pagination.getItemPerPage()}">1</a>
              </li>
              <c:if test="${pagination.currentPageStart() - 1 > 1}">
                <li><a href="#">...</a></li>
              </c:if>
              <c:forEach 
              	var="count" 
              	begin="${pagination.currentPageStart()}" 
              	end="${pagination.currentPageEnd()}" 
              	step="1"
              >
              	<li>
              		<a class="${pagination.compareCurrent(count)}" href="${contextPath}/list-computers?page=${count}&itemPerPage=${pagination.getItemPerPage()}">${count}</a>
              	</li>
              </c:forEach>
              <c:if test="${pagination.currentPageEnd() + 1 < pagination.lastPage()}">
                <li><a href="#">...</a></li>
              </c:if>
              <li>
              	<a 	class="${pagination.compareCurrent(pagination.lastPage())}"
              		href="${contextPath}/list-computers?page=${pagination.lastPage()}&itemPerPage=${pagination.getItemPerPage()}">${pagination.lastPage()}</a>
              </li>
              <li>
                <a
                	href="${contextPath}/list-computers?page=${pagination.next()}&itemPerPage=${pagination.getItemPerPage()}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        	</ul>
		</div>
        <div  class="btn-group btn-group-sm pull-right" role="group" style="transform:translateY(-50px);">
        	<c:forEach items="${pagination.getSizes()}" var="size" >
        	<a 
        	class="btn ${pagination.compareItemPerPage(size)}"
        	href="${contextPath}/list-computers?page=1&itemPerPage=${size}" class="btn btn-default">${size}</a>
        	</c:forEach>
        </div>
    </footer>
<script src="${contextPath}/assets/js/jquery.min.js"></script>
<script src="${contextPath}/assets/js/bootstrap.min.js"></script>
<script src="${contextPath}/assets/js/dashboard.js"></script>

</body>
</html>