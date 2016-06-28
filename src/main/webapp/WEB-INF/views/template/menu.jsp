<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<tilesx:useAttribute name="current"/>
<tilesx:useAttribute name="module"/>
<div class="col-md-2">
	<div class="sidebar-nav">
		<div class="navbar navbar-default" role="navigation">
              <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-navbar-collapse">
                  <span class="sr-only">Toggle ECS Nav.</span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                  <span class="icon-bar"></span>
                </button>
                <span class="visible-xs navbar-brand"></span>
              </div>
              
              <div class="navbar-collapse collapse sidebar-navbar-collapse">
				<spring:url value="/dashboard" var="homeUrl" htmlEscape="true" />
				<ul id="main-nav" class="main-nav nav nav-tabs nav-stacked">
					<li class="${current == 'index'?'active':''}">
						<a href="${homeUrl}"> 
							<i class="glyphicon glyphicon-th-large"></i> Dashboard
						</a>
					</li>
					
					<li>
						<a href="#receipts" class="nav-header collapsed" data-toggle="collapse"> 
							<i class="glyphicon glyphicon-credit-card"></i> Claim Management
							<span class="pull-right glyphicon  glyphicon-chevron-toggle"/>
						</a>
						<ul id="receipts" class="nav nav-list secondmenu collapse ${module=='receipts'?'in':''}">
							<li class="${current == 'receiptList'?'active':''}">
								<spring:url value="/getReceiptList" var="receiptListUrl" htmlEscape="true" /> 
								<a href="${receiptListUrl}">
									<i class="glyphicon glyphicon-list-alt"></i>&nbsp;Expense Claims
								</a>
							</li>
						</ul>
					</li>
					
					<li>
						<a href="#systemSetting" class="nav-header collapsed" data-toggle="collapse"> 
							<i class="glyphicon glyphicon-cog"></i> System Management 
							<span class="pull-right glyphicon glyphicon-chevron-toggle"></span>
						</a>
						<ul id="systemSetting" class="nav nav-list secondmenu collapse ${module=='systemSetting'?'in':''}">
							<li>
								<a href="#">
									<i class="glyphicon glyphicon-user"></i>&nbsp;User Mgr
								</a>
							</li>
							<li>
								<a href="#">
									<i class="glyphicon glyphicon-th-list"></i>&nbsp;Menu Mgr
								</a>
							</li>
							<li>
								<a href="#">
									<i class="glyphicon glyphicon-asterisk"></i>&nbsp;Role Mgr
								</a>
							</li>
							<li>
								<a href="#">
									<i class="glyphicon glyphicon-edit"></i>&nbsp;Edit Password
								</a>
							</li>
							<li><a href="#"><i class="glyphicon glyphicon-eye-open"></i>&nbsp;Logs</a></li>
						</ul>
					</li>
					
					<sec:authorize url="/aboutUs">
					<li class="${current == 'aboutUs'?'active':''}">
						<a href="<c:url value="/aboutUs"/>"> 
							<i class="glyphicon glyphicon-fire"></i>&nbsp;About Us 
							<span class="badge pull-right">1</span>
						</a>
					</li>
				</sec:authorize>
				</ul>
				
				<div class="site-menubar-footer">
			      <a href="javascript: void(0);" class="tooltip-info" data-trigger="hover" data-placement="top" data-toggle="tooltip" data-original-title="Settings">
			        <i class="glyphicon glyphicon-cog"></i>
			      </a>
			      <a href="javascript: void(0);" class="tooltip-info" data-trigger="hover" data-placement="top" data-toggle="tooltip" data-original-title="Lock">
			        <i class="glyphicon glyphicon-eye-close" ></i>
			      </a>
			      <a href="<c:url value="/logout"/>" class="tooltip-info" data-trigger="hover" data-placement="top" data-toggle="tooltip" data-original-title="Logout">
			        <i class="glyphicon glyphicon-log-out" ></i>
			      </a>
			    </div>
			</div>
		</div>
	</div>
</div>