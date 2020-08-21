<template>
  <div>
    <div class="fixed-bottom my-3 mx-3 row">
      <div class="text-left col-6" style="width: 50vw">
        <div v-if="isEditMode != null">
          <n-button
            type="primary"
            round
            v-if="isEditMode == 'enable'"
            @click="toggleEditView"
            >뷰어 모드로 보기</n-button
          >
          <n-button type="primary" round v-else @click="toggleEditView"
            >편집 모드로 보기</n-button
          >
        </div>
      </div>
      <div class="text-right col-6" style="width: 50vw">
        <el-popover
          ref="popovertrigger"
          trigger="click"
          popper-class="popover popover-default"
          placement="top"
        >
          <!-- <h3 class="popover-header">Popover</h3> -->
          <div class="popover-body text-center">
            <qr-code :text="link" style="width: 100%; height: 100%"></qr-code>
            <n-button
              @click="doCopy"
              class="btn btn-primary btn-round"
              size="sm"
              >클립보드로 URL 복사</n-button
            >
          </div>
        </el-popover>
        <n-button v-popover:popovertrigger type="primary" round
          >외부로 공유</n-button
        >
      </div>
    </div>
    <div class="page-header clear-filter" filter-color="orange-">
      <parallax
        class="page-header-image"
        style="background-image:url('img/Tent.jpg')"
      ></parallax>
      <div class="container">
        <div
          v-if="mine"
          class="photo-container"
          id="myphoto"
          @click="modifyPimg"
        >
          <div id="pimg">
            <img v-if="!user.profileImg" src="img/julie.jpg" alt />
            <img
              v-else
              :src="
                `http://i3d205.p.ssafy.io:8080/img/userProfileImg/${user.profileImg}`
              "
              alt
            />
          </div>
        </div>
        <div v-else class="photo-container" @click="modifyPimg">
          <div id="pimg">
            <img v-if="!user.profileImg" src="img/julie.jpg" alt />
            <img
              v-else
              :src="
                `http://i3d205.p.ssafy.io:8080/img/userProfileImg/${user.profileImg}`
              "
              alt
            />
          </div>
        </div>
        <div class="container">
          <div class="col-md-5 mx-auto">
            <div v-if="!update_nickname">
              <div>
                <h3 class="title">
                  {{ nickname }}
                  <i
                    v-if="mine"
                    class="far fa-edit"
                    @click="updateNickname_on"
                  ></i>
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
                <n-button
                  id="nickDuplChkBtn"
                  class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
                  @click="checkNickname"
                  >중복 체크</n-button
                >
                <n-button
                  id="nickModBtn"
                  class="m-0 btn btn-primary btn-round btn-md btn-block mr-1"
                  @click="modifyNickname"
                  disabled
                  >수정</n-button
                >
                <n-button
                  class="m-0 btn btn-primary btn-round btn-md btn-block mr-1 btn-danger"
                  @click="updateNickname_off"
                  >취소</n-button
                >
              </div>
            </div>
          </div>
        </div>
        <div class="col-md-5 mx-auto">
          <p v-if="!user.position">직무를 입력하지 않았습니다.</p>
          <div v-if="!update_position">
            <p class="category">
              {{ user.position }}
              <i v-if="mine" class="far fa-edit" @click="updatePosition_on"></i>
            </p>
          </div>
          <div v-else>
            <fg-input
              v-model="newPos"
              id="newPos"
              placeholder="직무를 입력해주세요"
              type="text"
              class="no-border form-control-md my-3"
              autofocus
            ></fg-input>
            <div class="d-flex">
              <n-button
                id="posModBtn"
                type="primary"
                round
                class="m-0 btn-md btn-block mr-1"
                @click="updatePosition"
                >수정</n-button
              >
              <n-button
                type="danger"
                round
                class="m-0 btn-md btn-block mr-1 btn-danger"
                @click="updatePosition_off"
                >취소</n-button
              >
            </div>
          </div>
        </div>
        <Counter
          :uid="this.pageuid"
          :nick="this.user.nickname"
          :followChk="followChk"
          @update="scrollPost"
        />
        <div v-if="mine" class="d-flex justify-content-end">
          <n-button
            class="btn btn-primary btn-round btn-md mr-1"
            type="primary"
            @click.native="modals.classic = true"
            >비밀번호 변경</n-button
          >
          <!--  -->
          <modal
            :show.sync="modals.classic"
            headerClasses="justify-content-center"
          >
            <h4 slot="header" class="title title-up text-dark">
              비밀번호 변경
            </h4>
            <fg-input
              v-model="nowPW"
              id="nowPW"
              placeholder="현재 비밀번호를 입력하세요."
              type="password"
              class="no-border form-control-md my-3"
              addon-left-icon="now-ui-icons ui-1_lock-circle-open"
            ></fg-input>

            <div
              class="btn btn-primary btn-round btn-md btn-block"
              @click="checkNowPW"
            >
              확인
            </div>
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
              <n-button type="primary" @click="modifyPW" id="pwModBtn" disabled
                >수정</n-button
              >
              <n-button
                type="danger"
                @click.native="modals.classic = false"
                @click="updatePW_off"
                >취소</n-button
              >
            </template>
          </modal>
          <!--  -->
          <n-button class="btn btn-danger btn-round btn-md" @click="deleteAlert"
            >탈퇴 하기</n-button
          >
        </div>
      </div>
    </div>

    <div class="section">
      <div class="container">
        <div class="button-container">
          <div v-if="!mine && !isEditMode" @click="toggleFollow">
            <a v-if="!followChk" class="btn btn-primary btn-round btn-lg"
              >Follow</a
            >
            <a v-else class="btn btn-default btn-round btn-lg">UnFollow</a>
          </div>
          <!-- <a
            href="#button"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on Twitter"
          >
            <i class="fab fa-twitter"></i>
          </a>
          <a
            :href="`${user.website}`"
            class="btn btn-default btn-round btn-lg btn-icon"
            rel="tooltip"
            title="Follow me on github"
            target="_blank"
          >
            <i class="fab fa-github"></i>
          </a>-->
        </div>
        <portList :uid="this.pageuid" :mine="mine" />
        <div>
          <h3 class="title">
            About me
            <i v-if="mine" class="far fa-edit" @click="updateBio_on"></i>
          </h3>
          <div v-if="!update_bio">
            <h5 v-if="bio" class="description">{{ bio }}</h5>
            <h5 v-else class="description">
              아직 자기소개를 입력하지 않았습니다.
            </h5>
          </div>
          <div v-else>
            <textarea
              class="form-control"
              v-model="newBio"
              id="newBio"
              placeholder="나를 소개하는 글을 입력해주세요"
              type="text"
            />
            <div class="text-center pt-3 pb-5">
              <n-button
                class="m-0 btn btn-primary btn-round btn-md mr-1"
                @click="modifyBio"
                >수정</n-button
              >
              <n-button
                class="m-0 btn btn-primary btn-round btn-md mr-1 btn-danger"
                @click="updateBio_off"
                >취소</n-button
              >
            </div>
          </div>
          <Contactme :user="user" :mine="mine" @update="getdata" />
          <div id="blogPost"></div>
          <div class="container">
            <tabs
              pills
              class="nav-align-center mx-auto"
              tab-content-classes="gallery"
              tab-nav-classes="nav-pills-just-icons"
              type="primary"
            >
              <tab-pane title="Profile">
                <i slot="label" class="far fa-address-card"></i>
                <h3 class="title pt-0">Resume</h3>
                <div class="col-md-10 mx-auto">
                  <Resume :uid="this.pageuid" :mine="mine" />
                </div>
              </tab-pane>

              <tab-pane title="Skill">
                <i slot="label" class="fas fa-sliders-h"></i>
                <h3 class="title pt-0">My Skill</h3>
                <div class="col-md-10 mx-auto">
                  <MySkill :uid="this.pageuid" :mine="mine" />
                </div>
              </tab-pane>

              <tab-pane title="blog">
                <i slot="label" class="far fa-clipboard"></i>
                <h3 class="title pt-0">Blog</h3>
                <div class="col-md-11 d-flex justify-content-end">
                  <div
                    class="btn-group"
                    role="group"
                    aria-label="Basic example"
                  >
                    <n-button
                      type="default"
                      round
                      class="btn"
                      @click="cardMode"
                    >
                      <i class="fas fa-th-large fa-2x"></i>
                    </n-button>
                    <n-button
                      id="button1"
                      type="default"
                      round
                      class="btn mx-1"
                      @click="postMode"
                    >
                      <i class="fas fa-list-ul fa-2x"></i>
                    </n-button>
                    <n-button
                      v-if="mine"
                      type="default"
                      round
                      class="btn"
                      @click="writeMode"
                    >
                      <i class="far fa-edit fa-2x"></i>
                    </n-button>
                  </div>
                </div>
                <div class="col-md-10 ml-auto mr-auto">
                  <transition enter-active-class="animated fadeInLeft">
                    <Userpost v-if="show1" :uid="this.pageuid" />
                  </transition>
                  <transition enter-active-class="animated fadeInRight">
                    <Listview v-if="show2" :uid="this.pageuid" />
                  </transition>
                  <transition enter-active-class="animated fadeIn">
                    <Write v-if="show3" @postWrite="cardMode" />
                  </transition>
                </div>
              </tab-pane>

              <tab-pane title="email" v-if="!mine">
                <i slot="label" class="far fa-envelope"></i>
                <h3 class="title pt-0">Send Email</h3>
                <SendEmail :email="user.email" />
              </tab-pane>
            </tabs>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { Tabs, TabPane, Modal, Button, FormGroupInput } from "@/components";
import { Popover } from "element-ui";

import Contactme from "../user/Contactme";
import Userpost from "../post/Userpost";
import Resume from "../user/Resume";
import MySkill from "../user/MySkill";
import SendEmail from "../user/SendEmail";
import Write from "../post/Write";
import Listview from "../post/Listview";
import Counter from "../user/Counter";

import portList from "../portfolio/portList";

export default {
  name: "profile",
  bodyClass: "profile-page",
  components: {
    Tabs,
    TabPane,
    Modal,
    [Button.name]: Button,
    [FormGroupInput.name]: FormGroupInput,
    [Popover.name]: Popover,

    Contactme,
    Userpost,
    Resume,
    MySkill,
    SendEmail,
    Write,
    Listview,
    Counter,

    portList,
  },
  created() {
    this.pageuid = this.$route.params.uid;
  },
  mounted() {
    this.getdata();
    this.checkFollow();
  },
  methods: {
    doCopy: function() {
      this.$copyText(this.link).then(
        function(e) {
          Swal.fire({
            icon: "success",
            title: "링크가 복사되었습니다!",
            showConfirmButton: false,
            timer: 1000,
          });
        },
        function(e) {
          Swal.fire({
            icon: "error",
            title: "링크 복사에 실패했습니다.",
            showConfirmButton: false,
            timer: 1000,
          });
        }
      );
    },
    getdata() {
      this.link = document.location.href;
      const params = new URL(document.location).searchParams;
      this.$axios
        .get(`/account/${this.pageuid}`)
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
          // if (data.object.bio) {
          //+ null, undefined, "" 모두 처리할 수 있게 변경
          this.bio = data.object.bio;
          // }
          this.user = data.object;

          if (this.$session.get("user").uid === this.user.uid) {
            this.mine = true;
            this.isEditMode = "enable";
          } else {
            this.mine = false;
          }
        })
        .catch((err) => {
          console.err("Err!!! :", err.response);
        });
    },
    // follow
    checkFollow() {
      this.$axios
        .post("/follow/checkFollow", null, {
          params: {
            followeeUid: this.pageuid,
            followerUid: this.$cookie.get("auth-token"),
          },
        })
        .then((res) => {
          this.followChk = res.data;
        })
        .catch((err) => console.error(err));
    },
    toggleFollow() {
      this.$axios
        .post("/follow/toggleFollow", null, {
          params: {
            followeeUid: this.pageuid,
            followerUid: this.$cookie.get("auth-token"),
          },
        })
        .then((res) => {
          // this.getdata();
          this.checkFollow();
          if (res.data) {
            Swal.fire({
              icon: "success",
              title: this.user.nickname + "님을 팔로우합니다.",
            }).then(() => {
              // this.$router.go();
            });
          } else {
            Swal.fire({
              icon: "success",
              title: this.user.nickname + "님의 팔로우를 취소합니다.",
            }).then(() => {
              // this.$router.go();
            });
          }
        })
        .catch((err) => console.error(err));
    },
    //닉네임변경관련메서드
    updateNickname_on() {
      this.newNick = this.nickname;
      this.update_nickname = true;
    },
    checkNickname() {
      if (this.newNick == "") {
        Swal.fire({
          icon: "info",
          title: "닉네임을 입력하세요.",
        });
        return;
      } else if (this.newNick.length > 128) {
        Swal.fire({
          icon: "warning",
          title: "닉네임이 너무 깁니다.",
          text: "닉네임을 128자 미만으로 입력하세요.",
        });
        return;
      } else {
        this.$axios
          .get(`/account/nicknameChk/${this.newNick}`)
          .then((response) => {
            this.result = response.data;
            if (
              this.result.data == "fail" &&
              this.result.object == "nickname"
            ) {
              Swal.fire({
                icon: "warning",
                title: "이미 사용중인 닉네임입니다.",
                text: "새로운 닉네임을 입력하세요.",
              });
            } else {
              Swal.fire({
                icon: "success",
                title: "사용 가능한 닉네임입니다.",
              });
              this.nicknameChk = true;
              document.getElementById("newNick").setAttribute("readonly", true);
              document
                .getElementById("nickDuplChkBtn")
                .setAttribute("disabled", true);
              document.getElementById("nickModBtn").removeAttribute("disabled");
            }
          })
          .catch((err) => {
            console.err("Err!!! :", err.response);
          });
      }
    },
    modifyNickname() {
      if (this.nicknameChk != true) {
        Swal.fire({
          icon: "warning",
          title: "닉네임 중복체크를 해 주세요.",
        });
      } else {
        this.$axios
          .put("/account/modify/nickname", {
            uid: this.uid,
            nickname: this.newNick,
          })
          .then((response) => {
            this.result = response.data;
            this.$session.set("user", response.data.object);
            Swal.fire({
              icon: "success",
              title: "회원정보수정 성공",
            }).then(() => {
              this.getdata();
              this.updateNickname_off();
            });
          })
          .catch((err) => {
            console.err("Err!!! :", err.response);
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
        Swal.fire({
          icon: "info",
          title: "현재 비밀번호를 입력하세요.",
        });
        return;
      }
      this.$axios
        .post("/account/login", null, {
          params: {
            email: this.email,
            password: this.nowPW,
          },
        })
        .then((response) => {
          this.nowPWChk = true;
          Swal.fire({
            icon: "success",
            title: "현재 비밀번호가 확인되었습니다.",
            text: "새로운 비밀번호를 입력해주세요.",
          });
          document.getElementById("nowPW").setAttribute("readonly", true);
          document.getElementById("pwModBtn").removeAttribute("disabled"); //+
        })
        .catch((err) => {
          console.err("ERROR :", err);
          Swal.fire({
            icon: "error",
            title: "비밀번호를 확인해주세요.",
            text: "비밀번호는 영문과 숫자를 포함해 8자 이상이어야 합니다.",
          });
          this.nowPW = ""; //+??
        });
    },
    modifyPW() {
      if (this.newPW1 == "") {
        Swal.fire({
          icon: "info",
          title: "새로운 비밀번호를 입력하세요.",
        });
        return;
      } else if (this.newPW2 == "") {
        Swal.fire({
          icon: "info",
          title: "새로운 비밀번호를 한번 더 입력하세요.",
        });
        return;
      } else if (this.newPW1 != this.newPW2) {
        Swal.fire({
          icon: "error",
          title: "비밀번호가 일치하지 않습니다.",
        });
        return;
      } else if (this.newPW1.length > 128) {
        Swal.fire({
          icon: "warning",
          title: "비밀번호가 너무 깁니다.",
          text: "비밀번호를 128자 미만으로 입력하세요.",
        });
        return;
      } else {
        this.$axios
          .put("/account/modify/password", {
            uid: this.uid,
            password: this.newPW1,
          })
          .then((response) => {
            let user = response.data.object;
            this.result = response.data;
            this.$session.set("user", user);
            Swal.fire({
              icon: "success",
              title: "비밀번호가 변경되었습니다.",
            }).then(() => {
              this.$router.go();
            });
          })
          .catch((err) => {
            console.err("Err!!! :", err.response);
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
    // 직무 변경 관련 메서드
    updatePosition_on() {
      if (this.user.position) {
        this.newPos = this.user.position;
      }
      this.update_position = true;
    },
    updatePosition() {
      if (this.newPos.length > 100) {
        Swal.fire({
          icon: "warning",
          title: "직무가 너무 깁니다.",
          text: "직무를 100자 이하로 입력하세요.",
        });
        return;
      }
      this.$axios
        .put("/account/modify/position", {
          uid: this.uid,
          position: this.newPos,
        })
        .then((response) => {
          this.result = response.data;
          this.$session.set("user", response.data.object);
          Swal.fire({
            icon: "success",
            title: "회원정보수정 성공",
          }).then(() => {
            this.getdata();
            this.updatePosition_off();
          });
        })
        .catch((err) => {
          console.err("Err!!! :", err.response);
        });
    },
    updatePosition_off() {
      this.update_position = false;
      this.newPos = "";
    },
    //자기소개변경관련메서드
    updateBio_on() {
      if (this.bio) this.newBio = this.bio;
      this.update_bio = true;
    },
    modifyBio() {
      if (this.newBio.length > 200) {
        Swal.fire({
          icon: "warning",
          title: "자기소개가 너무 깁니다.",
          text: "자기소개를 200자 이하로 입력하세요.",
        });
        return;
      }
      this.$axios
        .put("/account/modify/bio", {
          uid: this.uid,
          bio: this.newBio,
        })
        .then((response) => {
          this.result = response.data;
          Swal.fire({
            icon: "success",
            title: "나를 소개하는 글이 변경되었습니다.",
          }).then(() => {
            this.getdata();
            this.updateBio_off();
          });
        })
        .catch((err) => {
          console.err("Err!!! :", err.response);
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
      this.$axios
        .delete(`/account/dropout/${this.uid}`)
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
          console.err("Err!!!: ", err.response);
        });
    },

    async modifyPimg() {
      if (this.mine === false) {
        return;
      }
      const { value: file } = await Swal.fire({
        title: "Select image",
        input: "file",
        inputAttributes: {
          accept: "image/*",
          "aria-label": "프로필 이미지를 업로드하세요.",
        },
      });
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          Swal.fire({
            title: "프로필 사진 변경",
            imageUrl: e.target.result,
            imageAlt: "The uploaded picture",
          });
        };
        reader.readAsDataURL(file);

        const formData = new FormData();
        formData.append("profileImg", file);

        this.$axios
          .post(`/file/uploadProfileImg/${this.uid}`, formData, {
            headers: { "content-Type": "multipart/form-data" },
          })
          .then((response) => {
            this.result = response.data;
            this.$session.set("user", response.data.object);
            this.getdata();
          })
          .catch((err) => {
            console.err("Err!!! :", err.response);
          });
      }
    },
    writeMode() {
      this.show1 = false;
      this.show2 = false;
      this.show3 = true;
    },
    cardMode() {
      this.show1 = true;
      this.show2 = false;
      this.show3 = false;
    },
    postMode() {
      this.show1 = false;
      this.show2 = true;
      this.show3 = false;
    },
    toggleEditView() {
      this.mine = !this.mine;
      if (this.isEditMode == "enable") this.isEditMode = "disable";
      else this.isEditMode = "enable";
    },
    goOtherProfile(targetUid) {
      this.$router.push({
        path: `/profile/${targetUid}`,
      });
      this.$router.go();
    },
    scrollPost() {
      // 스크롤 로딩 오프셋값 거의 하드코딩 수준인데 어케 고치지;
      let location = document.querySelector("#blogPost").offsetTop + 490;
      window.scrollTo({ top: location, behavior: "smooth" });
    },
  },
  watch: {},
  data: () => {
    return {
      modals: {
        classic: false,
      },
      link: "",

      email: "",
      nickname: "",
      password: "",
      profileImg: "",
      bio: "",
      passwordType: "password",
      passwordConfirmType: "password",
      uid: null,

      update_nickname: false,
      update_profileimg: false,
      update_position: false,
      update_bio: false,
      update_follow: false,

      nicknameChk: false,
      nowPWChk: false,
      followChk: null,

      nowPW: "",
      newPW1: "",
      newPW2: "",

      newNick: "",
      newPos: "",
      newBio: "",

      pageuid: "",
      boards: [],

      user: null,
      birthDate: "",

      mine: false,
      isEditMode: null,

      show1: true,
      show2: false,
      show3: false,
    };
  },
};
</script>
<style scoped>
@import "https://cdn.jsdelivr.net/npm/animate.css@3.5.1";

#myphoto :hover {
  filter: grayscale(80%);
}
.btn:focus {
  background: coral;
}
i {
  cursor: pointer;
}
</style>
