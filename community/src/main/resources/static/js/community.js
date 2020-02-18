function post() {
    let questionId = $("#question_id").val();
    let content = $("#form-control").val();
    $.ajax({
        url: "/comment",
        type: "POST",
        dataType: "json",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (result) {
            if (result.code == 200) {
                $("#community_section").hide()
            } else {
                alert(result.msg);
            }
            console.log(result)
        }
    });
}