<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp"%>
<div class="navbar">
    <div class="navbar-inner">
        <div class="container-fluid">
            <a href="#" class="brand">
               <%-- <small>lework<span style="font-size: 12px;font-weight: normal;padding:0 0 0 5px;">0.5 beta</span> </small>--%>
               <%-- <h3 style="margin: 0;padding: 0;line-height:20px;">lework<span style="font-size: 12px;font-weight: normal;padding:0 0 0 5px;">0.5 beta</span></h3>--%>
                   lework<span style="font-size: 12px;font-weight: normal;padding:0 0 0 5px;">0.5 beta</span>
            </a><!--/.brand-->
            <ul class="nav ace-nav pull-right ">
       <%--         <li class="grey">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-tasks"></i>
                        <span class="badge badge-grey">4</span>
                    </a>

                    <ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-closer">
                        <li class="nav-header">
                            <i class="icon-ok"></i>
                            4 Tasks to complete
                        </li>
                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left">Software Update</span>
                                    <span class="pull-right">65%</span>
                                </div>

                                <div class="progress progress-mini ">
                                    <div style="width:65%" class="bar"></div>
                                </div>
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left">Hardware Upgrade</span>
                                    <span class="pull-right">35%</span>
                                </div>

                                <div class="progress progress-mini progress-danger">
                                    <div style="width:35%" class="bar"></div>
                                </div>
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left">Unit Testing</span>
                                    <span class="pull-right">15%</span>
                                </div>

                                <div class="progress progress-mini progress-warning">
                                    <div style="width:15%" class="bar"></div>
                                </div>
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <div class="clearfix">
                                    <span class="pull-left">Bug Fixes</span>
                                    <span class="pull-right">90%</span>
                                </div>

                                <div class="progress progress-mini progress-success progress-striped active">
                                    <div style="width:90%" class="bar"></div>
                                </div>
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                See tasks with details
                                <i class="icon-arrow-right"></i>
                            </a>
                        </li>
                    </ul>
                </li>


                <li class="green">
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <i class="icon-envelope icon-animated-vertical"></i>
                        <span class="badge badge-success">0</span>
                    </a>
                </li>--%>
                <li class="ms-blue">
                    <a data-toggle="dropdown" href="#" class="dropdown-toggle">
                        <img class="nav-user-photo" src="${ctx}/static/assets/images/user-img.jpg" alt="Jason's Photo">
                                                <span class="user-info">
                                                    <small> <shiro:principal /></small>
                                                </span>

                        <i class="icon-caret-down"></i>
                    </a>

                    <ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-closer">
                        <li>
                            <a href="#">
                                <i class="icon-cog"></i>
                                设置
                            </a>
                        </li>

                        <li>
                            <a href="${ctx}/account/profile">
                                <i class="icon-user"></i>
                               个人中心
                            </a>
                        </li>
                        <li>
                            <a href="${ctx}/developer/">
                                <i class="icon-book"></i>
                                开发者文档
                            </a>
                        </li>

                        <li class="divider"></li>

                        <li>
                            <a href="${ctx}/logout">
                                <i class="icon-off"></i>
                                注销
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <!--/.ace-nav-->
        </div>
        <!--/.container-fluid-->
    </div>
    <!--/.navbar-inner-->
</div>
<!--/.navbar-->