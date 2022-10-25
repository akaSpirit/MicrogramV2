'use strict'

const BASE_URL = "http://localhost:8998";


class User {
    constructor(email, username, password, isAuthorized) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAuthorized = isAuthorized;
    }
}

class Post {
    constructor(id, image, description, username, likeCount, isLiked, commentCount) {
        this.id = id;
        this.image = image;
        this.description = description;
        this.username = username;
        this.likeCount = likeCount;
        this.isLiked = isLiked;
        this.commentCount = commentCount;
    }
}

class Comment {
    constructor(id, comment, postId, username) {
        this.id = id;
        this.comment = comment;
        this.postId = postId;
        this.username = username;
    }
}

let users = [];
let posts = [];
let comments = [];
let randImage = "image" + (Math.floor(Math.random() * 5)) + ".jpg";
let actionTime = formatDate(new Date());


function addUsersData() {
    users.push(new User("qwe@gmail.com", "qwe", "qwerty", false));
    users.push(new User("asd@gmail.com", "asd", "qwerty", false));
    users.push(new User("zxc@gmail.com", "zxc", "qwerty", false));
    users.push(new User("wer@gmail.com", "wer", "qwerty", false));
    users.push(new User("sdf@gmail.com", "sdf", "qwerty", false));
}

function addPostsData() {
    posts.push(new Post(0, "image0.jpg", "bla-bla-bla", "qwe", 25, false, 2));
    posts.push(new Post(1, "image1.jpg", "bla-bla-bla", "asd", 20, false, 2));
    posts.push(new Post(2, "image2.jpg", "bla-bla-bla", "zxc", 15, false, 2));
    posts.push(new Post(3, "image3.jpg", "bla-bla-bla", "wer", 10, false, 2));
    posts.push(new Post(4, "image4.jpg", "bla-bla-bla", "sdf", 5, false, 2));
}

function addCommentsData() {
    comments.push(new Comment(0, "cool", 0, "asd"));
    comments.push(new Comment(1, "nice", 0, "zxc"));
    comments.push(new Comment(2, "wow", 1, "qwe"));
    comments.push(new Comment(3, "amazing", 1, "wer"));
    comments.push(new Comment(4, "omg", 2, "sdf"));
    comments.push(new Comment(5, "so cute", 2, "qwe"));
    comments.push(new Comment(6, "very nice", 3, "asd"));
    comments.push(new Comment(7, "beautiful", 3, "zxc"));
    comments.push(new Comment(8, "very impressive", 4, "asd"));
    comments.push(new Comment(9, "so cool", 4, "sdf"));
}

addUsersData();
let randUser = users[Math.floor(Math.random() * users.length)];

loadPosts();
loadComments();

function showSplashScreen() {
    document.getElementById("splash-screen").hidden = false;
    document.body.className = "no-scroll";
}

function hideSplashScreen() {
    document.getElementById("splash-screen").hidden = true;
    document.body.className = "";
}

function changeNumFormat(num) {
    return num < 10 ? `0${num}` : num % 100
}

function formatDate(date) {
    let now = new Date();
    if (now - date < 1000) return 'right now';
    if (now - date < 60000) return `${Math.floor((now - date) / 1000)} sec. ago`
    if (now - date < 3600000) return `${Math.floor((now - date) / 60000)} min. ago`
    if (now - date < 86400000) return `${Math.floor((now - date) / 3600000)} h. ago`
    if (now - date < 2592000000) return `${Math.floor((now - date) / 86400000)} d. ago`
    else {
        return `${changeNumFormat(date.getDate())}
        .${changeNumFormat(date.getMonth() + 1)}
        .${changeNumFormat(date.getFullYear())}`;
    }
}

function createPostElement(post) {
    const postElement = document.createElement('div');
    postElement.className = "card";
    postElement.innerHTML =
        `<div class="user-details">
            <div class="profile-pic">
                <div class="profile-img">
                    <div class="image">
                    <img src="../images/avatar1.jpg" alt="avatar">
                    </div>
                </div>
            </div>
            <span>${post.username}<br><span>Bishkek, Kyrgyzstan</span></span>
        </div>
        <div class="imgBx" id="imgBx-${post.id}">
        <img src="../images/${post.image}" alt="${post.image}" class="cover" id="img-${post.id}" ondblclick="likeByImage(${post.id})">
        </div>
        <div class="bottom">
            <div class="icons">
                <div class="left">
                <i class="bi bi-heart" id="heart-${post.id}" onclick=likeByIcon(${post.id})></i>
                <i class="bi bi-chat-right" id="chat-${post.id}" onclick=showHideCommentForm(${post.id})></i>
                <i class="bi bi-send" id="send-${post.id}"></i>
                </div>
            <div class="right">
                <i class="bi bi-bookmark" id="bookmark-${post.id}" onclick="addBookmark(${post.id})"></i>
            </div>
        </div>
        <div class="likes"><span id="like-count-${post.id}">${post.likeCount}</span> likes</div> 
            <p class="message"><b>${post.username} </b>${post.description}</p>
            <h6 class="comments" id="comments">View all ${post.commentCount} comments</h6> 
        <div class="comments-block">
        </div>
        <h5 class="post-time">${actionTime}</h5>
        <div class="add-comment" id="add-comment-${post.id}" hidden>
            <div class="reaction">
                <h3><i class="bi bi-emoji-smile"></i></h3>
            </div>
            <form id="comment-form-${post.id}">
            <input type="hidden" name="postId" value="${post.id}">
            <input type="hidden" name="username" value="${randUser.username}">
            <textarea type="text" class="comment" id="comment-${post.id}" placeholder="Add a comment..." name="commentText" rows="2" cols="40"></textarea>
            <button class="btn-post" >Post</button>
            </form>
            </div>
        </div>`
    return postElement;
}

function addPostElement(postElement) {
    document.getElementsByClassName("posts")[0].append(postElement);
}

function loadPosts() {
    addPostsData();
    for (let i = 0; i < posts.length; i++) {
        addPostElement(createPostElement(posts[i]));
        let commentForm = document.getElementById("comment-form-" + i);
        commentForm.addEventListener("submit", commentHandler);
        commentForm.addEventListener("submit", createNewCommentElement);
    }
}

function createCommentElement(comment) {
    let commentElement = document.createElement("div");
    commentElement.className = "comments-block";
    commentElement.innerHTML =
        `<p><b>${comment.username} </b>${comment.comment}. <i>${actionTime}</i></p>`;
    return commentElement;
}

function addCommentElement(commentElement, postId) {
    document.getElementsByClassName("comments")[postId].append(commentElement);
}

function loadComments() {
    addCommentsData();
    for (let i = 0; i < comments.length; i++) {
        addCommentElement(createCommentElement(comments[i]), comments[i].postId);
    }
}

function likeByIcon(postId) {
    let like = document.getElementById("heart-" + postId);
    let count = document.getElementById("like-count-" + postId);

    if (like.className === "bi bi-heart-fill") {
        like.className = "bi bi-heart";
        like.style.color = "black";
        count.innerText = parseInt(count.innerText) - 1;
    } else {
        like.className = "bi bi-heart-fill";
        like.style.color = "red";
        count.innerText = parseInt(count.innerText) + 1;
    }
}

function likeByImage(postId) {
    let like = document.getElementById("heart-" + postId);
    let img = document.getElementById("imgBx-" + postId);
    let count = document.getElementById("like-count-" + postId);

    const likeElement = document.createElement("i");
    likeElement.className = "bi bi-heart-fill";
    likeElement.style.color = "red";
    likeElement.style.opacity = "0.7";

    if (like.className === "bi bi-heart-fill") {
        like.className = "bi bi-heart";
        like.style.color = "black";
        count.innerText = parseInt(count.innerText) - 1;
    } else {
        like.className = "bi bi-heart-fill";
        like.style.color = "red";
        count.innerText = parseInt(count.innerText) + 1;

        img.append(likeElement);
        setTimeout(() => {
            img.getElementsByClassName("bi")[0].remove();
        }, 1500); // delete after 1,5 sec
    }
}

function addBookmark(postId) {
    let bookmark = document.getElementById("bookmark-" + postId);

    if (bookmark.className === "bi bi-bookmark-fill") {
        bookmark.className = "bi bi-bookmark";
    } else {
        bookmark.className = "bi bi-bookmark-fill";
    }
}

function showHideCommentForm(postId) {
    let chat = document.getElementById("chat-" + postId);
    let commentForm = document.getElementById("add-comment-" + postId);

    if (chat.className === "bi bi-chat-right") {
        chat.className = "bi bi-chat-right-fill";
        commentForm.hidden = false;
    } else {
        chat.className = "bi bi-chat-right";
        commentForm.hidden = true;
    }
}


let postForm = document.getElementById("post-form");

function createNewPostElement() {
    let img = document.querySelector("input[type=file]").files[0].name;
    let text = document.getElementById("post-text").value;

    let randPost = new Post(posts.length, img, text, randUser.username, 0, false, 0);
    posts.push(randPost);

    let newPostElem = createPostElement(posts[posts.length - 1]);
    addPostElement(newPostElem);
    console.log(posts);
    let commentForm = document.getElementById("comment-form-" + (posts.length - 1));
    commentForm.addEventListener("submit", commentHandler);
    commentForm.addEventListener("submit", createNewCommentElement);
}


let json;

function commentHandler(e) {
    e.preventDefault();
    const commentForm = e.target;
    const commentData = new FormData(commentForm);
    let commentObject = {};
    commentData.forEach(function (value, key) {
        commentObject[key] = value;
    })
    json = JSON.stringify(commentObject);
    console.log(json);

    let parse = JSON.parse(json);
    let postId = parseInt(parse.postId);
    sendComment(json, postId);
}

function createNewCommentElement() {
    let parse = JSON.parse(json);
    let postId = parseInt(parse.postId);
    let username = parse.username;
    let text = parse.commentText;
    let com = new Comment(comments.length, text, postId, username);
    comments.push(com);
    let newCom = createCommentElement(comments[comments.length - 1]);

    console.log(comments);
    addCommentElement(newCom, postId);
}

function postHandler(e) {
    e.preventDefault();
    const form = e.target;
    const formData = new FormData(form);
    let postObject = {};
    formData.forEach(function (value, key) {
        postObject[key] = value;
    })
    let json = JSON.stringify(postObject);
    console.log(json);
    sendPost(json);
}

function sendPost(json) {
    axios.post(BASE_URL + "/posts",
        json,
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
        .then(function (response) {
            console.log(response)
        })
        .catch(function (error) {
            console.log(error)
        });
}

function sendComment(json, postId) {
    axios.post(BASE_URL + "/comments/" + postId,
        json,
        {
            headers: {
                "Content-Type": "application/json"
            }
        }
    )
        .then(function (response) {
            console.log(response)
        })
        .catch(function (error) {
            console.log(error)
        });
}

postForm.addEventListener("submit", postHandler);
postForm.addEventListener("submit", createNewPostElement);