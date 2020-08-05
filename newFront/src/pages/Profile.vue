<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange">
      <parallax class="page-header-image" style="background-image:url('img/bg5.jpg')"></parallax>
      <div class="container">
        <div class="photo-container">
          <img src="img/ryan.jpg" alt />
        </div>
        <div v-if="!update_nickname">
          <h3 class="title">{{ nickname }}</h3>
          <button class="btn btn-primary" @click="updateNickname_on">닉네임 변경</button>
        </div>
        <div v-else style="width: 40%; ">
          <input
            v-model="nickname"
            id="nickname"
            placeholder="닉네임을 입력해주세요"
            type="text"
            class="form-control no-border input-l py-3 my-3"
          />
          <button
            class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
            @click="checkNickname"
          >중복 체크</button>
          <button
            class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
            @click="modifyNickname"
          >수정</button>
        </div>
        <p class="category">Programmer</p>
        <div class="content">
          <div class="social-description">
            <h2>26</h2>
            <p>Project</p>
          </div>
          <div class="social-description">
            <h2>26</h2>
            <p>Post</p>
          </div>
          <div class="social-description">
            <h2>48</h2>
            <p>Follower</p>
          </div>
        </div>
      </div>
    </div>
    <div class="section">
      <div class="container">
        <div class="button-container">
          <a href="#button" class="btn btn-primary btn-round btn-lg">Follow</a>
          <a
            href="#button"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Twitter"
          >
            <i class="fab fa-twitter"></i>
          </a>
          <a
            href="#button"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Instagram"
          >
            <i class="fab fa-instagram"></i>
          </a>
        </div>
        <h3 class="title">About me</h3>
        <h5 class="description">{{ bio }}</h5>
        <div class="row">
          <div class="col-md-6 ml-auto mr-auto">
            <h4 class="title text-center">My Portfolio</h4>
          </div>
          <tabs
            pills
            class="nav-align-center"
            tab-content-classes="gallery"
            tab-nav-classes="nav-pills-just-icons"
            type="primary"
          >
            <tab-pane title="Profile">
              <i slot="label" class="now-ui-icons design_image"></i>

              <div class="col-md-10 ml-auto mr-auto">
                <div class="row collections">
                  <div class="col-md-6">
                    <img src="img/bg6.jpg" class="img-raised" />
                    <img src="img/bg11.jpg" alt class="img-raised" />
                  </div>
                  <div class="col-md-6">
                    <img src="img/bg7.jpg" alt class="img-raised" />
                    <img src="img/bg8.jpg" alt class="img-raised" />
                  </div>
                </div>
              </div>
            </tab-pane>

            <tab-pane title="Home">
              <i slot="label" class="now-ui-icons location_world"></i>

              <div class="col-md-10 ml-auto mr-auto">
                <div class="row collections">
                  <div class="col-md-6">
                    <img src="img/bg1.jpg" alt class="img-raised" />
                    <img src="img/bg3.jpg" alt class="img-raised" />
                  </div>
                  <div class="col-md-6">
                    <img src="img/bg8.jpg" alt class="img-raised" />
                    <img src="img/bg7.jpg" alt class="img-raised" />
                  </div>
                </div>
              </div>
            </tab-pane>

            <tab-pane title="Messages">
              <i slot="label" class="now-ui-icons sport_user-run"></i>

              <div class="col-md-10 ml-auto mr-auto">
                <div class="row collections">
                  <div class="col-md-6">
                    <img src="img/bg3.jpg" alt class="img-raised" />
                    <img src="img/bg8.jpg" alt class="img-raised" />
                  </div>
                  <div class="col-md-6">
                    <img src="img/bg7.jpg" alt class="img-raised" />
                    <img src="img/bg6.jpg" class="img-raised" />
                  </div>
                </div>
              </div>
            </tab-pane>
          </tabs>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Tabs, TabPane } from "@/components";
import axios from "axios";

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Tabs,
    TabPane,
  },
  components: {},
  mounted() {
    this.getdata();
  },
  methods: {
    getdata() {
      const params = new URL(document.location).searchParams;
      axios
        .get(`http://localhost:8080/account/${this.$cookie.get("auth-token")}`)
        .then(({ data }) => {
          console.log(data.object);
          this.uid = data.object.uid;
          this.email = data.object.email;
          this.nickname = data.object.nickname;
          this.password = data.object.password;
          // if (data.object.profile_img === undefined) {
          //   this.profile_img = require("../../assets/img/profile_default.png");
          //   console.log(this.profile_img);
          // } else {
          //   this.profile_img = data.object.profile_img;
          // }
          if (data.object.bio !== undefined) {
            this.bio = data.object.bio;
          }
          console.log(this.profile_img);
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    updateNickname_on() {
      this.update_nickname = true;
    },
    checkNickname() {
      if (this.nickname == "") {
        alert("닉네임을 입력하세요.");
        return;
      } else {
        axios
          .get(`http://localhost:8080/account/nicknameChk/${this.nickname}`)
          .then((response) => {
            this.result = response.data;
            if (
              this.result.data == "fail" &&
              this.result.object == "nickname"
            ) {
              alert("이미 가입된 닉네임입니다. 새로운 닉네임을 입력하세요.");
              document.getElementById("nickname").focus();
            } else {
              this.nicknameChk = true;
              document
                .getElementById("nickname")
                .setAttribute("readonly", true);
              alert("사용 가능한 닉네임입니다.");
              console.log(this.nickname);
            }
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },
    modifyNickname() {
      if (this.nicknameChk != true) {
        alert("닉네임 중복 체크를 해 주세요.");
      } else {
        axios
          .put("http://localhost:8080/account/modify/nickname", {
            uid: this.uid,
            nickname: this.nickname,
          })
          .then((response) => {
            this.result = response.data;
            console.log(this.result);
            alert("회원정보수정 성공!");
            this.update_nickname = false;
            this.nicknameChk = false;
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },

    updateBio_on() {
      this.update_bio = true;
    },
    modifyBio() {
      console.log(document.getElementById("bio").value);
      axios
        .put("http://localhost:8080/account/modify/bio", {
          uid: this.uid,
          bio: document.getElementById("bio").value,
        })
        .then((response) => {
          this.result = response.data;
          console.log(this.result);
          alert("회원정보수정 성공!");
          this.update_bio = false;
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },

    deleteUser() {
      axios
        .delete(`http://localhost:8080/account/dropout/${this.uid}`)
        .then((response) => {
          console.log(response);
          this.$session.destroy();
          this.$cookie.delete("auth-token");
          this.$router.push("/");
        })
        .catch((err) => {
          console.log("Err!!!: ", err.response);
        });
    },

    deleteAlert() {
      Swal.fire({
        title: "정말 탈퇴하시겠어요?",
        text:
          "확인 버튼을 누르면 모든 데이터가 영구적으로 삭제되어 복구할 수 없게 됩니다.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "탈퇴할래요😥",
        cancelButtonText: "안할래요😊",
      }).then((result) => {
        if (result.value) {
          this.deleteUser();
          Swal.fire(
            "탈퇴 완료!",
            "데이터가 영구적으로 삭제되었습니다.",
            "success"
          );
        }
      });
    },
  },
  watch: {},
  data: () => {
    return {
      email: "",
      nickname: "",
      password: "",
      profile_img: "",
      bio: "",
      passwordType: "password",
      passwordConfirmType: "password",
      uid: null,

      update_nickname: false,
      update_profileimg: false,
      update_bio: false,
    };
  },
};
</script>
<style></style>
