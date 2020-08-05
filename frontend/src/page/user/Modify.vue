<template>
  <div class="user" id="join">
    <div class="container">
      <div class="d-flex align-items-start flex-column">
        <!-- Button trigger modal -->
        <button @click="deleteAlert">회원 탈퇴</button>
      </div>
      <div class="row my-5">
        <div class="col-12 col-sm-4">
          <img
            class="img-thumbnail rounded-circle"
            style="width: 18rem;"
            v-bind:src="profile_img"
            alt
          />

          <!-- Button trigger modal -->
          <button data-toggle="modal" data-target="#profile-imgModal">프로필 사진 수정</button>

          <!-- Modal -->
          <div
            class="modal fade"
            id="profile-imgModal"
            tabindex="-1"
            role="dialog"
            aria-labelledby="profile-imgModalLabel"
            aria-hidden="true"
          >
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="profile-imgModalLabel">프로필 사진 수정</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">안녕 나는 바디</div>
                <div class="modal-footer">안녕 나는 푸터</div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-12 col-sm-8 align-self-center">
          <div class="row justify-content-center">
            <span v-if="!update_nickname">
              <h2 class="mr-3">{{ nickname }}</h2>
              <button class="mx-3" @click="updateNickname_on">닉네임 수정</button>

              <!-- modifyPW -->
              <!-- Button trigger modal -->
              <button class="mx-3" data-toggle="modal" data-target="#modifyPWModal">비밀번호 수정</button>

              <!-- Modal -->
              <div
                class="modal fade"
                id="modifyPWModal"
                tabindex="-1"
                role="dialog"
                aria-labelledby="modifyPWModalLabel"
                aria-hidden="true"
              >
                <div class="modal-dialog">
                  <div class="modal-content">
                    <div class="modal-header">
                      <h5 class="modal-title" id="modifyPWModalLabel">비밀번호 수정</h5>
                      <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                      </button>
                    </div>

                    <div class="modal-body">
                      <div class="user" id="login">
                        <div class="wrapC table">
                          <div class="middle">
                            <div class="input-wrap">
                              <input
                                v-model="nowPW"
                                id="nowPW"
                                placeholder="현재 비밀번호를 입력하세요."
                                type="password"
                              />
                              <span v-if="nowPWChk" style="color: rgb(0, 191, 0)">
                                <i class="fas fa-check-circle"></i>
                              </span>
                              <span v-else>
                                <i class="fas fa-check-circle"></i>
                              </span>
                              <button @click="checkNowPW" class="mt-2 mx-3">인증</button>
                            </div>
                            <div class="input-wrap">
                              <input
                                v-model="newPW1"
                                id="newPW1"
                                placeholder="새로운 비밀번호를 입력하세요."
                                type="password"
                                @keyup.enter="modifyPW"
                              />
                            </div>
                            <div class="input-wrap">
                              <input
                                v-model="newPW2"
                                id="newPW2"
                                placeholder="새로운 비밀번호를 입력하세요."
                                type="password"
                                @keyup.enter="modifyPW"
                              />
                            </div>
                            <button class="btn btn--back btn--login" @click="modifyPW">비밀번호 변경</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- modifyPW -->
            </span>
            <span v-else>
              <div class="input-wrap">
                <input v-model="nickname" id="nickname" placeholder="닉네임을 입력해주세요" type="text" />
                <span v-if="nicknameChk" style="color: rgb(0, 191, 0)">
                  <i class="fas fa-check-circle"></i>
                </span>
                <span v-else>
                  <i class="fas fa-check-circle"></i>
                </span>
              </div>
              <button @click="checkNickname" class="mx-3">닉네임 중복 체크</button>
              <button @click="modifyNickname" class="mx-3">수정</button>
            </span>
          </div>
          <p>{{ email }}</p>
        </div>
      </div>
      <span v-if="!update_bio">
        <div class="row">
          <p class="text-left">{{ bio }}</p>
        </div>
        <div class="d-flex align-items-end flex-column">
          <button @click="updateBio_on" class="my-3">Bio 수정</button>
        </div>
      </span>
      <span v-else>
        <div class="input-wrap">
          <input v-model="bio" id="bio" placeholder="나를 소개하는 글을 입력해주세요" type="text" />
        </div>
        <button @click="modifyBio">수정</button>
      </span>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
import axios from "axios";
import router from '../../router';

export default {
  components: {},
  created() {
    this.getdata();
  },
  methods: {
    getdata() {
      axios
        .get(`http://localhost:8080/account/${this.$cookie.get("auth-token")}`)
        .then(({ data }) => {
          this.uid = data.object.uid;
          this.email = data.object.email;
          this.nickname = data.object.nickname;
          this.password = data.object.password;
          if (data.object.profile_img === undefined) {
            this.profile_img = require("../../assets/img/profile_default.png");
          } else {
            this.profile_img = data.object.profile_img;
          }
          if (data.object.bio !== undefined) {
            this.bio = data.object.bio;
          }
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
            let user = response.data.object;
            this.result = response.data;
            this.$session.set("user", user);
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
          let user = response.data.object;
          this.result = response.data;
          this.$session.set("user", user);
          this.update_bio = false;
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },

    // authcheck w/ login
    checkNowPW() {
      if (this.nowPW == "") {
        alert("비밀번호를 입력하세요.");
        return;
      }
      console.log(this.email, this.nowPW);
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
        })
        .catch((err) => {
          console.log("ERROR :", err);
          alert(
            "비밀번호를 확인해주세요. \n비밀번호는 영문과 숫자를 포함해 8자 이상이어야 합니다."
          );
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
            this.$router.go();
            this.nowPW = "";
            this.newPW1 = "";
            this.newPW2 = "";
            this.nowPWChk = false;

            alert("회원정보수정 성공!");
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
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
          Swal.fire({
            title: "탈퇴 완료!",
            text : "데이터가 영구적으로 삭제되었습니다.",
            icon :"success",
            showConfirmButton : true,
            confirmButtonText : "확인",
          }).then(() => {
            router.go();
          });
        }
      });
    },

    successAlert(msg) {
      Swal.fire({
        icon: "success",
        title: msg + " 수정 완료!",
        showConfirmButton: false,
        timer: 1500,
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

      nicknameChk: false,
      nowPWChk: false,

      update_nickname: false,
      update_profileimg: false,
      update_bio: false,

      nowPW: "",
      newPW1: "",
      newPW2: "",
    };
  },
};
</script>
