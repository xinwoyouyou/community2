//提交回复
function post() {
    let questionId = $("#question_id").val();
    let content = $("#form-control").val();
    comment2target(questionId, 1, content);
}


function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容~~")
        return;
    }
    $.ajax({
        url: "/comment",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (result) {
            if (result.code == 100) {
                window.location.reload();
            } else {
                if (result.code == 102) {
                    const isAccepted = confirm(result.msg);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=Iv1.dbf53619d7309d98&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(result.msg);
                }

            }
        }
    });

}

function comment(e) {
    const id = e.getAttribute("data-id");
    const content = $("#form-control" + id).val();
    comment2target(id, 2, content);
}

//展开二级评论
function collapseComment(e) {
    const id = e.getAttribute("data-id");
    const comments = $("#comment-" + id);
    //开关 单击添加,再单击移除
    comments.toggleClass("in");
    //获取二级评论的展开状态
    const hasClass = comments.hasClass("in");
    if (hasClass) {
        $.getJSON("/comment/" + id, function (data) {
            var html = "";
            var html2="";
            var commentDTOS = data.extend.commentDTOS;
            if (commentDTOS == null || commentDTOS == "") {
                return;
            }
            for (var i = 0; i < commentDTOS.length; i++) {
                var commentDTO = commentDTOS[i];
                var gmtCreate = commentDTO.gmtCreate;
                html += "<div class='col-lg-12 col-md-12 col-sm-12 col-xs-12 comments'>" +
                    "<div class='media'>" +
                    "<div class=\"media-left\">" +
                    "<img class=\"media-object img-circle\"\n" +
                    " src=" +
                    commentDTO.user.avatarUrl +
                    ">" +
                    "</div>" +
                    "<div class='media-body'>" +
                    "<h5 class='media-heading'>" +
                    "<span>"
                    + commentDTO.user.name +
                    "</span>" +
                    "</h5>" +
                    "<div>"
                    + commentDTO.content +
                    "</div>" +
                    "<div class=\"menu\">" +
                    "<span class=\"pull-right\">" +
                      new Date(gmtCreate)+
                    "</span>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>";
            }
             var commentId = $("#comment-" + id);

            html2+="  <div class=\"col-lg-12 col-md-12 col-sm-12 col-xs-12 comments\">\n" +
                "                                    <input type=\"text\" class=\"form-control\" placeholder=\"评论一下...\"\n" +
                "                                           th:id=\"form-control+${comment.id}\">\n" +
                "                                    <button type=\"button\" class=\"btn btn-success pull-right\" id=\"pull-right\"\n" +
                "                                            onclick=\"comment(this)\" th:data-id=\"${comment.id}\">评论\n" +
                "                                    </button>\n" +
                "                                </div>";
            commentId.html(html+html2);
        });


    } else {
        e.classList.remove("active");
    }
}




