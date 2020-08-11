<template>
  <div>
    <div class="page-header clear-filter" filter-color="orange-">
      <parallax class="page-header-image" style="background-image:url('img/bg5.jpg')"></parallax>
      <div class="container">
        <div class="photo-container">
          <img src="img/julie.jpg" alt />
        </div>
        <div class="container">
          <div class="col-md-5 mx-auto">
            <div v-if="!update_nickname">
              <div>
                <h3 class="title">
                  {{ nickname }}
                  <i class="far fa-edit" @click="updateNickname_on"></i>
                </h3>
              </div>
            </div>
            <div v-else>
              <p>새로운 닉네임을 입력하고 중복 체크해주세요.</p>
              <fg-input
                v-model="newNick"
                id="newNick"
                placeholder="닉네임을 입력해주세요"
                type="text"
                class="no-border form-control-md my-3"
                addon-left-icon="now-ui-icons users_circle-08"
                autofocus
              ></fg-input>
              <div class="d-flex">
                <button
                  id="nickDuplChkBtn"
                  class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
                  @click="checkNickname"
                >중복 체크</button>
                <button
                  id="nickModBtn"
                  class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
                  @click="modifyNickname"
                  disabled
                >수정</button>
                <button
                  class="m-0 btn btn-primary btn-round btn-md btn-block mr-1 btn-danger"
                  @click="updateNickname_off"
                >취소</button>
              </div>
            </div>
          </div>
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
        <div class="d-flex justify-content-end">
          <n-button
            class="btn btn-primary"
            type="primary"
            @click.native="modals.classic = true"
          >비밀번호 변경</n-button>
          <!--  -->
          <modal :show.sync="modals.classic" headerClasses="justify-content-center">
            <h4 slot="header" class="title title-up text-dark">비밀번호 변경</h4>
            <fg-input
              v-model="nowPW"
              id="nowPW"
              placeholder="현재 비밀번호를 입력하세요."
              type="password"
              class="no-border form-control-md my-3"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
            ></fg-input>

            <div class="btn btn-primary btn-round btn-md btn-block" @click="checkNowPW">확인</div>
            <div v-if="nowPWChk">
              <fg-input
                v-model="newPW1"
                id="newPW1"
                placeholder="새로운 비밀번호를 입력하세요."
                type="password"
                class="no-border form-control-md my-3"
                @keyup.enter="modifyPW"
                addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              ></fg-input>
              <fg-input
                v-model="newPW2"
                id="newPW2"
                placeholder="새로운 비밀번호를 입력하세요."
                type="password"
                @keyup.enter="modifyPW"
                class="no-border form-control-md my-3"
                addon-left-icon="now-ui-icons ui-1_lock-circle-open"
              ></fg-input>
            </div>
            <template slot="footer">
              <n-button type="primary" @click="modifyPW" id="pwModBtn" disabled>수정</n-button>
              <n-button
                type="danger"
                @click.native="modals.classic = false"
                @click="updatePW_off"
              >취소</n-button>
            </template>
          </modal>
          <!--  -->
          <button class="btn btn-danger" @click="deleteAlert">탈퇴 하기</button>
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
        <h3 class="title">
          About me
          <i class="far fa-edit" @click="updateBio_on"></i>
        </h3>
        <div v-if="!update_bio">
          <h5 v-if="bio" class="description">{{ bio }}</h5>
          <h5 v-else class="description">아직 자기소개를 입력하지 않았습니다.</h5>
        </div>
        <div v-else>
          <textarea
            class="form-control"
            v-model="newBio"
            id="newBio"
            placeholder="나를 소개하는 글을 입력해주세요"
            type="text"
          />
          <button class="m-0 btn btn-primary btn-round btn-md mr-1" @click="modifyBio">수정</button>
          <button
            class="m-0 btn btn-primary btn-round btn-md mr-1 btn-danger"
            @click="updateBio_off"
          >취소</button>
        </div>

        <Contactme :user="user"/>

        <div class="row">
          <!-- 
          <div class="col-md-6 ml-auto mr-auto">
            <h4 class="title text-center">My Portfolio</h4>
          </div>
          -->
          <tabs
            pills
            class="nav-align-center mx-auto"
            tab-content-classes="gallery"
            tab-nav-classes="nav-pills-just-icons"
            type="primary"
          >
            <tab-pane title="Profile">
              <i slot="label" class="far fa-address-card"></i>
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

            <tab-pane title="Home">
              <i slot="label" class="fas fa-sliders-h"></i>
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
              <i slot="label" class="far fa-folder-open"></i>
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
                <Userpost />
              </div>
            </tab-pane>
          </tabs>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Tabs, TabPane, Modal, Button, FormGroupInput } from "@/components";
import Userpost from "../post/Userpost";
import axios from "axios";
import Contactme from "../user/Contactme";

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Tabs,
    TabPane,
    Userpost,
    Modal,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,

    Contactme,
  },
  created() {
    this.pageuid = this.$route.params.uid;
  },
  mounted() {
    this.getdata();
  },
  methods: {
    getdata() {
      const params = new URL(document.location).searchParams;
      axios
        .get(`http://localhost:8080/account/${this.pageuid}`)
        .then(({ data }) => {
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
          if (data.object.bio) {
            //+ null, undefined, "" 모두 처리할 수 있게 변경
            this.bio = data.object.bio;
          }
console.log(data.object);
          this.user = data.object;
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    //닉네임변경관련메서드
    updateNickname_on() {
      this.newNick = this.nickname;
      this.update_nickname = true;
    },
    checkNickname() {
      if (this.newNick == "") {
        alert("닉네임을 입력하세요.");
        return;
      } else {
        axios
          .get(`http://localhost:8080/account/nicknameChk/${this.newNick}`)
          .then((response) => {
            this.result = response.data;
            if (
              this.result.data == "fail" &&
              this.result.object == "nickname"
            ) {
              alert("이미 가입된 닉네임입니다. 새로운 닉네임을 입력하세요.");
              document.getElementById("newNick").focus();
            } else {
              alert("사용 가능한 닉네임입니다.");
              this.nicknameChk = true;
              document.getElementById("newNick").setAttribute("readonly", true);
              document
                .getElementById("nickDuplChkBtn")
                .setAttribute("disabled", true);
              document.getElementById("nickModBtn").removeAttribute("disabled");
              // console.log(this.nickname);
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
            nickname: this.newNick,
          })
          .then((response) => {
            this.result = response.data;
            this.$session.set("user", response.data.object);
            alert("회원정보수정 성공!");
            this.$router.go();
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },
    updateNickname_off() {
      //+
      this.update_nickname = false;
      this.nicknameChk = false;
    },
    //비밀번호변경관련메서드
    checkNowPW() {
      if (this.nowPW == "") {
        alert("현재 비밀번호를 입력하세요.");
        document.getElementById("nowPW").focus(); //+
        return;
      }
      // console.log(this.email, this.nowPW);
      axios({
        method: "POST",
        url: `http://localhost:8080/account/login`,
        params: {
          email: this.email,
          password: this.nowPW,
        },
      })
        .then((response) => {
          this.nowPWChk = true;
          alert(
            "현재 비밀번호가 확인되었습니다. 새로운 비밀번호를 입력해주세요."
          ); //+
          document.getElementById("nowPW").setAttribute("readonly", true);
          document.getElementById("pwModBtn").removeAttribute("disabled"); //+
        })
        .catch((err) => {
          console.log("ERROR :", err);
          alert(
            "비밀번호를 확인해주세요. \n비밀번호는 영문과 숫자를 포함해 8자 이상이어야 합니다."
          );
          this.nowPW = ""; //+??
          document.getElementById("nowPW").focus(); //+
        });
    },
    modifyPW() {
      if (this.newPW1 == "") {
        alert("새로운 비밀번호를 입력하세요.");
        document.getElementById("newPW1").focus();
        return;
      } else if (this.newPW2 == "") {
        alert("새로운 비밀번호를 한번 더 입력하세요.");
        document.getElementById("newPW2").focus();
        return;
      } else if (this.newPW1 != this.newPW2) {
        alert("비밀번호가 일치하지 않습니다.");
        document.getElementById("newPW2").focus();
        return;
      } else {
        console.log(this.newPW1);
        axios
          .put("http://localhost:8080/account/modify/password", {
            uid: this.uid,
            password: this.newPW1,
          })
          .then((response) => {
            let user = response.data.object;
            this.result = response.data;
            this.$session.set("user", user);
            alert("비밀번호 변경 성공!");
            this.$router.go();
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },
    updatePW_off() {
      //+ && 모달 백드롭 backdrop 확인
      this.nowPW = "";
      this.newPW1 = "";
      this.newPW2 = "";
      this.nowPWChk = false;
      document.getElementById("nowPW").removeAttribute("readonly");
      document.getElementById("pwModBtn").setAttribute("disabled", true);
    },
    //자기소개변경관련메서드
    updateBio_on() {
      if (this.bio) this.newBio = this.bio;
      this.update_bio = true;
    },
    modifyBio() {
      axios
        .put("http://localhost:8080/account/modify/bio", {
          uid: this.uid,
          bio: document.getElementById("newBio").value,
        })
        .then((response) => {
          this.result = response.data;
          console.log(this.result);
          alert("소개글 수정 성공!");
          this.$router.go();
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    updateBio_off() {
      this.newBio = "";
      this.update_bio = false;
    },
    //회원탈퇴관련메서드
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
        }
      });
    },
    deleteUser() {
      axios
        .delete(`http://localhost:8080/account/dropout/${this.uid}`)
        .then((response) => {
          this.$session.destroy();
          this.$cookie.delete("auth-token");
          Swal.fire({
            title: "탈퇴 완료!",
            text: "데이터가 영구적으로 삭제되었습니다.",
            icon: "success",
            showConfirmButton: true,
            confirmButtonText: "확인",
          }).then(() => {
            this.$router.push("/");
            this.$router.go();
          });
        })
        .catch((err) => {
          console.log("Err!!!: ", err.response);
        });
    },
  },
  watch: {},
  data: () => {
    return {
      modals: {
        classic: false,
      },
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

      nicknameChk: false,
      nowPWChk: false,

      nowPW: "",
      newPW1: "",
      newPW2: "",

      newNick: "",
      newBio: "",

      pageuid: "",
      boards: [],

      user: null,
      birthDate: "",
    };
  },
};
</script>
<style></style>