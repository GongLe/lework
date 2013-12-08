<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/included/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>角色修改</title>
</head>

<body>

<div class="modal-content">
    <form action="role/update" class="no-margin" method="post" id="form-input" name="form-input"  >
        <div class="modal-header">
            <h4 class="blue bigger">Please fill the following form fields</h4>
        </div>

    <div class="modal-body ">
        <div class="row-fluid ">
            <div class="span12">
                <h1>modal-body</h1>

                <h1>modal-body</h1>

                <h1>modal-body</h1>
            </div>
        </div>
        <!--/.row-fluid -->
    </div>

        <div class="modal-footer">
            <button type="button" class="btn btn-small" onclick="$.colorbox.close()">
                <i class="icon-remove"></i>
                关闭
            </button>

        </div>
    </form>
</div>

</body>
</html>
