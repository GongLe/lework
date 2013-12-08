<%@ page import="org.lework.core.utils.SubjectUtils" %>
<%@ page import="org.lework.core.entity.ShiroUser" %>
<%@ page import="org.lework.core.entity.menu.Menu" %>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp"%>
<%
    ShiroUser user = SubjectUtils.getUser();
    if (session.getAttribute("menuList") == null) {
        if (user != null) {
            session.setAttribute("menuList", user.getMenus());
        }
    }

%>

<div class="sidebar" id="sidebar">
<form action="search-results.html" method="GET" class="search-form">
    <div class="search-pane">
        <input type="text" name="search" placeholder="Search here...">
        <button type="submit"><i class="icon-search"></i></button>
    </div>
</form>

<ul class="nav nav-list">
    <%-- 三级级菜单循环输出--%>
    <c:forEach items="${sessionScope.menuList}" var="menu">
        <li>
            <a href="${ctx}/${menu.url}" data-menu-id="${menu.id}"  <c:if test="${  menu.hasChild eq 'true' }">class="dropdown-toggle" </c:if> >
                <c:if test="${not empty menu.icon}">
                    <i class="${menu.icon}"></i>
                </c:if>
                <span class="menu-text-in"> ${menu.name} </span>
                <c:if test="${menu.hasChild eq  true }">
                    <b class="arrow icon-angle-down"></b>
                </c:if>
            </a>
            <c:if test="${menu.hasChild == true }">

                <ul class="submenu">
                    <c:forEach items="${menu.childrenMenus}" var="subMenu">

                        <li>
                            <a href="${ctx}/${subMenu.url}" data-menu-id="${subMenu.id}"
                               <c:if test="${not empty subMenu.childrenMenus }">class="dropdown-toggle" </c:if> >
                                <c:if test="${not empty subMenu.icon}">
                                    <i class="${subMenu.icon}"></i>
                                </c:if>
                                <span class="menu-text-in"> ${subMenu.name} </span>

                                <c:if test="${subMenu.hasChild eq  true}">
                                    <b class="arrow icon-angle-down"></b>
                                </c:if>
                            </a>
                            <c:if test="${ subMenu.childrenMenus !=null}">
                                <ul class="submenu">
                                    <c:forEach items="${subMenu.childrenMenus}" var="subMenu3">
                                        <li>
                                            <a href="${ctx}/${subMenu3.url}" data-menu-id="${subMenu3.id}"
                                               <c:if test="${subMenu3.hasChild == true}">class="dropdown-toggle" </c:if> >
                                                <c:if test="${not empty subMenu3.icon}">
                                                    <i class="${subMenu3.icon}"></i>
                                                </c:if>
                                                <span class="menu-text-in"> ${subMenu3.name} </span>
                                                <c:if test="${subMenu3.hasChild eq  true }">
                                                    <b class="arrow icon-angle-down"></b>
                                                </c:if>
                                            </a>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </c:if>
                        </li>
                    </c:forEach>
                </ul>
            </c:if>
        </li>
    </c:forEach>
<%--<li>
    <a href="#" class="dropdown-toggle">
        <i class="icon-desktop"></i>
        <span class="menu-text-in"> UI Elements <span class="badge badge-primary ">4</span></span>

        <b class="arrow icon-angle-down"></b>
    </a>

    <ul class="submenu">
        <li>
            <a href="${ctx}/ace/elements.jsp">
                <i class="icon-double-angle-right"></i>
                Elements
            </a>
        </li>

        <li>
            <a href="${ctx}/ace/buttons.jsp">
                <i class="icon-double-angle-right"></i>
                Buttons &amp; Icons
            </a>
        </li>

        <li>
            <a href="treeview.jsp">
                <i class="icon-double-angle-right"></i>
                Treeview
            </a>
        </li>

        <li>
            <a href="#" class="dropdown-toggle">
                <i class="icon-double-angle-right"></i>

                Three Level Menu
                <b class="arrow icon-angle-down"></b>
            </a>

            <ul class="submenu">
                <li>
                    <a href="#">
                        <i class="icon-leaf"></i>
                        Item #1
                    </a>
                </li>

                <li>
                    <a href="#" class="dropdown-toggle">
                        <i class="icon-pencil"></i>

                        4th level
                        <b class="arrow icon-angle-down"></b>
                    </a>

                    <ul class="submenu">
                        <li>
                            <a href="#">
                                <i class="icon-plus"></i>
                                Add Product
                            </a>
                        </li>

                        <li>
                            <a href="#">
                                <i class="icon-eye-open"></i>
                                View Products
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </li>
    </ul>
</li>--%>


</ul>
<!--/.nav-list-->

<div class="sidebar-collapse" id="sidebar-collapse">
    <i class="icon-double-angle-left"></i>
</div>
</div>
<!--./sidebar-->