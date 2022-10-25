/**
 * 게시글 DOM 생성 및 추가
 * @param post_object JSON 형식의 게시글 데이터
 */
function drawPost(post_object) {
    let post_div = document.createElement("div");
    let user_nickname = document.querySelector("meta[name='user_nickname']")?.getAttribute("content");
    post_div.classList.add("post");
    post_div.setAttribute("data-board-id", post_object["board"]["boardId"]);
    post_div.setAttribute("data-post-uuid", post_object["postUuid"]);

    // 게시글 DOM HTML
    post_div.innerHTML = `
	<div class="post-left">
		<ion-icon name="arrow-up-circle-outline" onclick="postAction(this, 'like')"></ion-icon>
		<p class="like-count">100</p>
		<ion-icon name="arrow-down-circle-outline" onclick="postAction(this, 'dislike')"></ion-icon>
	</div>
	<div class="post-right">
		<div class="post-top">
		    <span class="post-board-name">
		        <img src="" alt="">
		        <a href=""></a>
		        •
            </span>
			<span class="post-info"><a href=""></a> • </span>
			<span class="post-timestamp" title=""></span><span> <span class="post-update-timestamp"></span></span>
		</div>
		<div class="post-content">
			<span class="post-title">
				<a class="title" href=""></a>
				<a class="tab" href=""></a>
			</span>
			<div class="post-body"></div>
		</div>
		<div class="post-action">
			<span class="comment-count" onclick="postAction(this, 'comment')">
                <ion-icon name="chatbox-outline"></ion-icon>
                댓글 <span></span>개
			</span>
			<span class="share" onclick="postAction(this, 'share')">
                <ion-icon name="share-social-outline"></ion-icon>
                공유
			</span>
<!--			<span class="bookmark" onclick="postAction(this, 'bookmark')">-->
<!--                <ion-icon name="bookmark-outline"></ion-icon>-->
<!--                북마크-->
<!--			</span>-->
            <span class="edit" onclick="postAction(this, 'edit')">
                <ion-icon name="create-outline"></ion-icon>
                수정
            </span>
            <span class="delete" onclick="postAction(this, 'delete')">
                <ion-icon name="trash-outline"></ion-icon>
                삭제
            </span>
        </div>
    </div>
    `;

    // 게시판 이름
    let board_name = post_div.querySelector(".post-board-name a");
    board_name.innerText = post_object["board"]["boardName"];
    board_name.href = "/r/{}".format(post_object["board"]["boardId"]);

    // 게시판 아이콘
    let board_icon = post_div.querySelector(".post-board-name img");
    board_icon.src = "/r/{}/icon".format(post_object["board"]["boardId"]);

    // 게시글 작성자
    post_div.querySelector(".post-info a").innerText = post_object["user"]["userNickname"];
    post_div.querySelector(".post-info a").href = "/u/{}".format(post_object["user"]["userNickname"]);

    // 게시글 작성 시간
    let post_timestamp_span = post_div.querySelector(".post-timestamp");
    post_timestamp_span.setAttribute("title", post_object["postTimestamp"]);
    let post_timestamp = new Date(post_object["postTimestamp"]);
    let post_timestamp_diff = new Date() - post_timestamp;
    if (post_timestamp_diff < 0) {
        post_timestamp_span.innerText = "방금 전";
    } else if (post_timestamp_diff < 60000) {
        post_timestamp_span.innerText = Math.floor(post_timestamp_diff / 1000) + "초 전";
    } else if (post_timestamp_diff < 3600000) {
        post_timestamp_span.innerText = Math.floor(post_timestamp_diff / 60000) + "분 전";
    } else if (post_timestamp_diff < 86400000) {
        post_timestamp_span.innerText = Math.floor(post_timestamp_diff / 3600000) + "시간 전";
    } else if (post_timestamp_diff < 2592000000) {
        post_timestamp_span.innerText = Math.floor(post_timestamp_diff / 86400000) + "일 전";
    } else if (post_timestamp_diff < 31536000000) {
        post_timestamp_span.innerText = Math.floor(post_timestamp_diff / 2592000000) + "달 전";
    } else {
        post_timestamp_span.innerText = Math.floor(post_timestamp_diff / 31536000000) + "년 전";
    }

    // 게시글 수정 시간
    let post_update_timestamp_span = post_div.querySelector(".post-update-timestamp");
    if (post_object["postUpdateTimestamp"] !== undefined) {
        post_update_timestamp_span.title = post_object["postUpdateTimestamp"];
        post_update_timestamp_span.textContent = "(수정됨)";
    }

    // 게시글 제목
    post_div.querySelector(".post-title .title").innerHTML = post_object["postTitle"];
    post_div.querySelector(".post-title .title").href = "/r/{}/post/{}".format(post_object["board"]["boardId"], post_object["postUuid"]);

    // 게시글 탭
    post_div.querySelector(".post-title .tab").innerText = post_object["postTab"];
    post_div.querySelector(".post-title .tab").href = "/r/{}?tab={}".format(post_object["board"]["boardId"], post_object["postTab"]);
    post_div.querySelector(".post-title .tab").style.backgroundColor = post_object["board"]["boardColor"];

    // 게시글 내용
    post_div.querySelector(".post-body").innerHTML = post_object["postContent"];

    // 게시글 댓글
    post_div.querySelector(".comment-count span").innerText = post_object["postCommentCount"];

    // 본인 게시글이 아니면 수정, 삭제 버튼 삭제
    if (user_nickname === undefined || user_nickname !== post_object["user"]["userNickname"]) {
        post_div.querySelector(".edit").remove();
        post_div.querySelector(".delete").remove();
    }

    // 좋아요 상태 확인
    // post_object에 "postUserLikeStatus"가 있는지 확인
    let like_btn = post_div.querySelector(".post-left > ion-icon[name='arrow-up-circle-outline']");
    let dislike_btn = post_div.querySelector(".post-left > ion-icon[name='arrow-down-circle-outline']");
    let like_cnt = post_div.querySelector(".post-left > p.like-count");
    like_cnt.innerText = post_object["postLikeCount"];
    if (post_object["postUserLikeStatus"] !== undefined) {
        if (post_object["postUserLikeStatus"] === "LIKE") {
            like_btn.setAttribute("name", "arrow-up-circle");
            like_btn.classList.add("colorize");
            like_cnt.classList.add("colorize");

        } else if (post_object["postUserLikeStatus"] === "DISLIKE") {
            dislike_btn.setAttribute("name", "arrow-down-circle");
            dislike_btn.classList.add("colorize");
            like_cnt.classList.add("colorize");
        }
    }

    // 게시글 목록에 추가
    document.querySelector(".post-list").appendChild(post_div);
}

/**
 * 게시글 댓글, 공유, 북마크, 좋아요 버튼 처리
 * @param elem 클릭한 버튼
 * @param action 동작
 */
function postAction(elem, action) {
    let post_uuid = elem.closest(".post").getAttribute("data-post-uuid");
    let board_id = elem.closest(".post").getAttribute("data-board-id");
    if (action === "edit") {
        location.href = "/r/{}/post/{}/edit".format(board_id, post_uuid);
    } else if (action === "delete") {
        if (confirm("정말 삭제하시겠습니까?")) {
            fetch("/r/{}/post/{}/delete".format(board_id, post_uuid), {
                method: "POST",
            }).then(function (response) {
                if (response.ok) {
                    alert("게시글이 삭제되었습니다.");
                    elem.closest(".post").remove();
                } else {
                    alert("게시글 삭제에 실패했습니다.");
                }
            });
        }
    } else if (action === "comment") {
        location.href = "/r/{}/post/{}#comments".format(board_id, post_uuid);
    } else if (action === "bookmark") {
        alert("북마크 - 준비중입니다.");
    } else if (action === "share") {
        let url = elem.closest(".post").querySelector("a.title").href;
        try {
            window.navigator.clipboard.writeText(url).then(function () {
                alert("게시글 주소가 복사되었습니다.");
            });
        } catch (e) {
            // localhost 또는 HTTPS 연결이 아닌 경우 navigator.clipboard 사용 불가
            prompt("아래 게시글 주소를 복사하세요.", url);
        }
    } else if (action === "like" || action === "dislike") {
        let user_nickname = document.querySelector("meta[name='user_nickname']")?.getAttribute("content");
        if (user_nickname === undefined) {
            alert("로그인 후 이용해주세요.");
            return;
        }
        let post = elem.closest(".post");
        let current_like_status = "none";
        let active_btn = post.querySelector("ion-icon.colorize");
        let like_cnt_span = post.querySelector(".like-count");
        if (active_btn !== null) {
            if (active_btn.name.includes("up")) {
                current_like_status = "like";
            } else if (active_btn.name.includes("down")) {
                current_like_status = "dislike";
            }
        }
        if (action === current_like_status) {
            // 좋아요/싫어요 취소
            fetch("/like/remove", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "target={}".format(post_uuid)
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data["result"] === "success") {
                    active_btn.setAttribute("name", active_btn.name.replace("-circle", "-circle-outline"));
                    active_btn.classList.remove("colorize");
                    like_cnt_span.innerText = parseInt(like_cnt_span.innerText) - (action === "like" ? 1 : -1);
                    like_cnt_span.classList.remove("colorize");
                } else {
                    alert(data["message"]);
                }
            });
        } else { // 아무것도 안 눌렀거나 다른 버튼을 누름
            // 좋아요/싫어요 등록 또는 갱신
            fetch("/like/upsert", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "target={}&type={}".format(post_uuid, action.toUpperCase())
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data["result"] === "success") {
                    if (active_btn !== null) { // 기존에 누른 버튼이 있었음
                        active_btn.classList.remove("colorize");
                        active_btn.name = active_btn.name.replace("-circle", "-circle-outline");
                    } else { // 기존에 누른 버튼이 없었음
                        like_cnt_span.classList.add("colorize");
                    }
                    elem.classList.add("colorize");
                    elem.name = elem.name.replace("-circle-outline", "-circle");

                    let result = parseInt(like_cnt_span.innerText) + (action === "like" ? 1 : -1);
                    if (current_like_status !== "none") {
                        result += (action === "like" ? 1 : -1);
                    }
                    like_cnt_span.innerText = result;
                } else {
                    alert(data["message"]);
                }
            });
        }
    }
}


/**
 * 댓글 DOM 생성 및 추가 재귀함수
 * @param comment_object 비동기로 가져온 댓글 데이터 JSON 객체
 * @param comment_depth 댓글의 깊이(계층형 레벨)
 */
function drawComment(comment_object, comment_depth) {
    let comment_div = document.createElement("div");
    let user_nickname = document.querySelector("meta[name='user_nickname']")?.getAttribute("content");
    comment_div.classList.add("comment");
    comment_div.classList.add("depth-" + comment_depth);
    comment_div.setAttribute("data-comment-uuid", comment_object["commentUuid"]);
    comment_div.id = comment_object["commentUuid"];

    // 댓글 DOM HTML
    comment_div.innerHTML = `
    <div class="bar" onclick="location.href='#' + this.parentElement.id"></div>
    <div class="comment-left">
        <div class="user-icon-wrap">
            <div class="user-icon">
                <img src="" alt="">
            </div>
        </div>
    </div>
    <div class="comment-right">
        <div class="comment-header">
            <p><a class="user-nickname" href="#"></a> • <span class="comment-timestamp" title=""></span> <span class="comment-update-timestamp" title=""></span></p>
        </div>
        <div class="comment-body">
        </div>
        <div class="comment-footer">
            <span class="like-btn" onclick="commentAction(this, 'like')">
                <ion-icon name="arrow-up-circle-outline"></ion-icon>
            </span>
            <span class="like-count">0</span>
            <span class="dislike-btn" onclick="commentAction(this, 'dislike')">
                <ion-icon name="arrow-down-circle-outline"></ion-icon>
            </span>
            <span class="reply-btn" onclick="commentAction(this, 'reply')">
                <ion-icon name="chatbubble-ellipses-outline"></ion-icon>
                답글
            </span>
            <span class="edit-btn" onclick="commentAction(this, 'edit')">
                <ion-icon name="create-outline"></ion-icon>
                수정
            </span>
            <span class="delete-btn" onclick="commentAction(this, 'delete')">
                <ion-icon name="trash-outline"></ion-icon>
                삭제
            </span>
        </div>
    </div>
    `;

    // 댓글 작성자 정보
    comment_div.querySelector(".comment-header > p > a").innerText = comment_object.user?.userNickname;
    comment_div.querySelector(".comment-header > p > a").href = "/u/" + comment_object.user?.userNickname;
    comment_div.querySelector(".user-icon > img").src = "/u/" + comment_object.user?.userNickname + "/icon";
    // 탈퇴한 사용자일 경우
    if (comment_object.user === undefined) {
        comment_div.querySelector(".comment-header > p > a").innerText = "알 수 없음";
        comment_div.querySelector(".comment-header > p > a").removeAttribute("href");
        comment_div.querySelector(".user-icon > img").src = "/static/img/default_user_icon.jpg";
    }

    // 댓글 작성 시간
    let timestamp_span = comment_div.querySelector(".comment-timestamp");
    timestamp_span.title = comment_object["commentTimestamp"];
    let comment_timestamp = new Date(comment_object["commentTimestamp"]);
    let comment_timestamp_diff = new Date() - comment_timestamp;
    if (comment_timestamp_diff < 0) {
        timestamp_span.innerText = "방금 전";
    } else if (comment_timestamp_diff < 60000) {
        timestamp_span.innerText = Math.floor(comment_timestamp_diff / 1000) + "초 전";
    } else if (comment_timestamp_diff < 3600000) {
        timestamp_span.innerText = Math.floor(comment_timestamp_diff / 60000) + "분 전";
    } else if (comment_timestamp_diff < 86400000) {
        timestamp_span.innerText = Math.floor(comment_timestamp_diff / 3600000) + "시간 전";
    } else if (comment_timestamp_diff < 2592000000) {
        timestamp_span.innerText = Math.floor(comment_timestamp_diff / 86400000) + "일 전";
    } else if (comment_timestamp_diff < 31536000000) {
        timestamp_span.innerText = Math.floor(comment_timestamp_diff / 2592000000) + "달 전";
    } else {
        timestamp_span.innerText = Math.floor(comment_timestamp_diff / 31536000000) + "년 전";
    }
    comment_div.querySelector(".comment-body").innerHTML = comment_object["commentContent"];

    // 댓글 수정 시간
    let update_timestamp_span = comment_div.querySelector(".comment-update-timestamp");
    if (comment_object["commentUpdateTimestamp"] !== undefined) {
        update_timestamp_span.title = comment_object["commentUpdateTimestamp"];
        update_timestamp_span.textContent = " (수정됨)";
    }

    // 댓글 좋아요 수
    let comment_like_cnt_span = comment_div.querySelector(".like-count");
    comment_like_cnt_span.innerText = comment_object["commentLikeCount"];

    // 대대댓글이면 답글 버튼 삭제
    if (comment_depth >= 3) {
        comment_div.querySelector(".reply-btn").remove();
    }

    // 본인 댓글이 아니면 수정, 삭제 버튼 삭제
    if (user_nickname === undefined || (user_nickname !== comment_object.user?.userNickname)) {
        comment_div.querySelector(".edit-btn").remove();
        comment_div.querySelector(".delete-btn").remove();
    }

    // 댓글 좋아요
    let like_btn = comment_div.querySelector(".like-btn");
    let dislike_btn = comment_div.querySelector(".dislike-btn");
    let like_cnt = comment_div.querySelector(".like-count");
    like_cnt.innerText = comment_object["commentLikeCount"];
    if (comment_object["commentUserLikeStatus"] !== undefined) {
        if (comment_object["commentUserLikeStatus"] === "LIKE") {
            like_btn.querySelector("ion-icon").setAttribute("name", "arrow-up-circle");
            like_btn.querySelector("ion-icon").classList.add("colorize");
            like_cnt.classList.add("colorize");
        } else if (comment_object["commentUserLikeStatus"] === "DISLIKE") {
            dislike_btn.querySelector("ion-icon").setAttribute("name", "arrow-down-circle");
            dislike_btn.querySelector("ion-icon").classList.add("colorize");
            like_cnt.classList.add("colorize");
        }
    }

    // 최상위 댓글이면 .comment-list에 추가, 아니면 부모 댓글에 추가
    if (comment_object["commentParentUuid"] === null || comment_object["commentParentUuid"] === undefined) {
        document.querySelector(".post-comments > .comment-list").appendChild(comment_div);
    } else {
        document.querySelector("div.comment[data-comment-uuid=\'" + comment_object["commentParentUuid"] + "\'] > .comment-right").appendChild(comment_div);
    }

    // 자식 댓글이 있으면 depth + 1 하여 재귀 호출
    if (comment_object["child"] !== null && comment_object["child"].length > 0) {
        for (let i = 0; i < comment_object["child"].length; i++) {
            drawComment(comment_object["child"][i], comment_depth + 1);
        }
    }
}

/**
 * 댓글 답글, 수정, 삭제 버튼 클릭 처리
 * @param elem 클릭한 버튼(this)
 * @param action 답글 : reply, 수정 : edit, 삭제 : delete
 */
function commentAction(elem, action) {
    let comment_uuid = elem.closest(".comment").getAttribute("data-comment-uuid");
    let post_uuid = elem.closest(".post-comments").previousElementSibling.getAttribute("data-post-uuid");
    let board_id = document.querySelector("meta[name='board_id']").getAttribute("content");
    if (action === "reply") {
        modal_large("/r/{}/post/{}/comment/{}/reply".format(board_id, post_uuid, comment_uuid));
    } else if (action === "edit") {
        modal_large("/r/{}/post/{}/comment/{}/edit".format(board_id, post_uuid, comment_uuid));
    } else if (action === "delete") {
        if (confirm("정말 삭제하시겠습니까?")) {
            fetch("/r/{}/post/{}/comment/{}/delete".format(board_id, post_uuid, comment_uuid), {
                method: "POST"
            }).then(function (response) {
                if (response.ok) {
                    return response.json();
                } else {
                    throw new Error("오류가 발생했습니다.");
                }
            }).then(function (json) {
                if (json["result"] === "success") {
                    let comment = elem.closest(".comment");
                    comment.querySelector(".comment-body").textContent = "삭제된 댓글입니다.";
                    comment.querySelector(".user-nickname").textContent = "알 수 없음";
                    comment.querySelector(".user-nickname").removeAttribute("href");
                    comment.querySelector(".user-icon > img").remove();
                    comment.querySelector(".edit-btn").remove();
                    comment.querySelector(".delete-btn").remove();
                } else {
                    alert(json["message"]);
                }
            });
        }
    } else if (action === "like" || action === "dislike") {
        let user_nickname = document.querySelector("meta[name='user_nickname']")?.getAttribute("content");
        if (user_nickname === undefined) {
            alert("로그인 후 이용해주세요.");
            return;
        }

        let comment = elem.closest(".comment");
        let current_like_status = "none";
        let active_btn = comment.querySelector("ion-icon.colorize");
        let like_cnt_span = comment.querySelector(".like-count");
        if (active_btn !== null) {
            if (active_btn.name.includes("up")) {
                current_like_status = "like";
            } else if (active_btn.name.includes("down")) {
                current_like_status = "dislike";
            }
        }
        if (action === current_like_status) {
            // 좋아요/싫어요 취소
            fetch("/like/remove", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "target={}".format(comment_uuid)
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data["result"] === "success") {
                    active_btn.setAttribute("name", active_btn.name.replace("-circle", "-circle-outline"));
                    active_btn.classList.remove("colorize");
                    like_cnt_span.innerText = parseInt(like_cnt_span.innerText) - (action === "like" ? 1 : -1);
                    like_cnt_span.classList.remove("colorize");
                } else {
                    alert(data["message"]);
                }
            });
        } else { // 아무것도 안 눌렀거나 다른 버튼을 누름
            // 좋아요/싫어요 등록 또는 갱신
            fetch("/like/upsert", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "target={}&type={}".format(comment_uuid, action.toUpperCase())
            }).then(function (response) {
                return response.json();
            }).then(function (data) {
                if (data["result"] === "success") {
                    if (active_btn !== null) { // 기존에 누른 버튼이 있었음
                        active_btn.classList.remove("colorize");
                        active_btn.name = active_btn.name.replace("-circle", "-circle-outline");
                    } else { // 기존에 누른 버튼이 없었음
                        like_cnt_span.classList.add("colorize");
                    }
                    elem.querySelector("ion-icon").classList.add("colorize");
                    elem.querySelector("ion-icon").name = elem.querySelector("ion-icon").name.replace("-circle-outline", "-circle");

                    let result = parseInt(like_cnt_span.innerText) + (action === "like" ? 1 : -1);
                    if (current_like_status !== "none") {
                        result += (action === "like" ? 1 : -1);
                    }
                    like_cnt_span.innerText = result;
                } else {
                    alert(data["message"]);
                }
            });
        }
    }
}

function subscribe(elem) {
    let board_id = document.querySelector("meta[name='board_id']").content;
    let user_nickname = document.querySelector("meta[name='user_nickname']")?.getAttribute("content");
    if (user_nickname === undefined) {
        alert("로그인 후 이용해주세요.");
        return;
    }
    if (elem.classList.contains("active")) {
        fetch("/r/{}/unsubscribe".format(board_id), {
            method: "POST"
        }).then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
            if (data["result"] === "success") {
                elem.classList.remove("active");
                elem.textContent = "구독";
            } else {
                alert(data["message"]);
            }
        });
    } else {
        fetch("/r/{}/subscribe".format(board_id), {
            method: "POST"
        }).then(function (response) {
            if (response.ok) {
                return response.json();
            }
        }).then(function (data) {
            if (data["result"] === "success") {
                elem.classList.add("active");
                elem.textContent = "구독중";
            } else {
                alert(data["message"]);
            }
        });
    }
}