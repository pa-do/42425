<template>
  <div class="user" id="join">
    <div class="wrapC table">
      <div class="middle">
        <h1>회원가입</h1>
        <div class="form-wrap">
          <div class="input-wrap">
            <input v-model="nickname" id="nickname" placeholder="닉네임을 입력해주세요" type="text" />
            <button @click="checkNickname" class="mt-2">중복 체크</button>
            <span v-if="nicknameChk" style="color: rgb(0, 191, 0)">
              <i class="fas fa-check-circle"></i>
            </span>
            <span v-else>
              <i class="fas fa-check-circle"></i>
            </span>
          </div>

          <div class="input-wrap">
            <input v-model="email" id="Jemail" placeholder="이메일을 입력해주세요" type="text" />
            <button @click="checkEmail" class="mt-2 mx-3">중복 체크</button>
            <button @click="sendEmail" class="mt-2 mx-3">인증 메일 전송</button>
            <span v-if="emailChk" style="color: rgb(0, 191, 0)">
              <i class="fas fa-check-circle"></i>
            </span>
            <span v-else>
              <i class="fas fa-check-circle"></i>
            </span>
          </div>
          <div class="input-wrap">
            <input
              v-model="input_authnum"
              id="input_authnum"
              placeholder="인증번호를 입력해주세요"
              type="text"
            />
            <span v-if="authChk" style="color: rgb(0, 191, 0)">
              <i class="fas fa-check-circle"></i>
            </span>
            <span v-else>
              <i class="fas fa-check-circle"></i>
            </span>
            <button @click="authEmail" class="mt-2 mx-3">인증</button>
          </div>

          <div class="input-wrap password-wrap">
            <input v-model="password" id="password" :type="passwordType" placeholder="비밀번호를 입력해주세요" />
            <!-- <span :class="{ active: passwordType === 'text' }">
              <i class="fas fa-eye"></i>
            </span>-->
          </div>

          <div class="input-wrap password-wrap">
            <input
              v-model="passwordConfirm"
              id="password-confirm"
              :type="passwordConfirmType"
              placeholder="비밀번호를 한번 더 입력해주세요"
            />
            <!-- <span :class="{ active: passwordConfirmType === 'text' }">
              <i class="fas fa-eye"></i>
            </span>-->
          </div>
        </div>

        <label>
          <input v-model="isTerm" type="checkbox" id="term" />
          <span>약관에 동의합니다</span>
        </label>

        <!-- Button trigger modal -->
        <button class="go-term" data-toggle="modal" data-target="#go-termModal">약관 보기</button>

        <!-- Modal -->
        <div
          class="modal fade"
          id="go-termModal"
          tabindex="-1"
          role="dialog"
          aria-labelledby="go-termModalLabel"
          aria-hidden="true"
        >
          <div class="modal-dialog modal-dialog modal-lg modal-dialog-scrollable text-left">
            <div class="modal-content">
              <div class="modal-header">
                <h5 class="modal-title" id="go-termModalLabel">약관</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">① 이용계약은 회원 또는 쇼핑몰의 해지에 의하여 즉시 뼈와 살이 분리됩니다.</div>
              <div class="modal-footer">
                <button class="mx-3" data-dismiss="modal" @click="agreeTerm(true)">예</button>
                <button class="mx-3" data-dismiss="modal" @click="agreeTerm(false)">아니요</button>
              </div>
            </div>
          </div>
        </div>

        <button class="btn" @click="join">
          <span>작성완료</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/user.scss";
import axios from "axios";

export default {
  components: {},
  created() {},
  methods: {
    checkNickname() {
      console.log(this.nickname);
      axios
        .get(`http://localhost:8080/account/nicknameChk/${this.nickname}`)
        .then((response) => {
          this.result = response.data;
          if (this.result.data == "fail" && this.result.object == "nickname") {
            alert("이미 가입된 닉네임입니다. 새로운 닉네임을 입력하세요.");
            document.getElementById("nickname").focus();
          } else {
            alert("사용 가능한 닉네임입니다.");
            this.nicknameChk = true;
            document.getElementById("nickname").setAttribute("readonly", true);
            console.log(document.getElementById("nickname"));
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    checkEmail() {
      if (!this.validEmail(this.email)) {
        alert("메일 형식을 확인하세요.");
        return;
      }
      axios
        .get(`http://localhost:8080/account/emailChk/${this.email}`)
        .then((response) => {
          this.result = response.data;
          console.log(this.result);
          if (this.result.data == "fail" && this.result.object == "email") {
            document.getElementById("Jemail").focus();
            alert("이미 가입된 이메일입니다. 새로운 이메일을 입력하세요.");
          } else {
            this.emailChk = true;
            document.getElementById("Jemail").setAttribute("readonly", true);
            console.log(document.getElementById("Jemail"));
            alert("사용 가능한 이메일입니다.");
          }
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    join() {
      if (this.nickname == "") {
        alert("닉네임을 입력하세요.");
        document.getElementById("nickname").focus();
        return;
      } else if (!this.nicknameChk) {
        alert("닉네임 중복 체크하세요.");
        return;
      } else if (this.email == "") {
        alert("메일 주소를 입력하세요.");
        document.getElementById("email").focus();
        return;
      } else if (!this.validEmail(this.email)) {
        alert("메일 형식을 확인하세요.");
        return;
      } else if (!this.emailChk) {
        alert("이메일 중복 체크하세요.");
        return;
      } else if (this.password == "") {
        alert("비밀번호를 입력하세요.");
        document.getElementById("password").focus();
        return;
      } else if (this.passwordConfirm == "") {
        alert("비밀번호를 한번 더 입력하세요.");
        document.getElementById("password-confirm").focus();
        return;
      } else if (this.isTerm == false) {
        alert("약관에 동의해 주세요.");
        return;
      } else if (this.password != this.passwordConfirm) {
        alert("비밀번호가 일치하지 않습니다.");
        return;
      } else {
        console.log(this.nickname, this.email, this.password);
        axios
          .post("http://localhost:8080/account/signup", {
            nickname: this.nickname,
            email: this.email,
            password: this.password,
          })
          .then((response) => {
            this.result = response.data;
            if (this.result.data != "fail") {
              alert("가입 성공! 회원가입을 축하합니다.");
              this.user = response.data.object;
              this.$session.set("user", this.user);
              this.$router.push("/");
              this.$cookie.set("auth-token", this.user.uid, 1);
            }
          })
          .catch((err) => {
            console.log("Err!!! :", err.response);
          });
      }
    },
    validEmail: function (email) {
      var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
      return re.test(email);
    },
    agreeTerm(res) {
      this.isTerm = res;
    },
    sendEmail() {
      axios
        .get(`http://localhost:8080/email/send/${this.email}`)
        .then((response) => {
          alert("이메일이 발송되었습니다. 인증번호를 입력해주세요.");
          this.authnum = response.data.data;
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    authEmail() {
      if (this.authnum === this.input_authnum) {
        alert("인증이 완료되었습니다.");
        document.getElementById("input_authnum").setAttribute("readonly", true);
        this.authChk = true;
      } else {
        alert("인증번호가 일치하지 않습니다. 다시 입력해주세요.");
      }
    },
  },
  watch: {},
  data: () => {
    return {
      email: "",
      nickname: "",
      password: "",
      passwordConfirm: "",
      isTerm: false,
      passwordType: "password",
      passwordConfirmType: "password",
      emailChk: false,
      nicknameChk: false,
      authnum: "",
      input_authnum: "",
      authChk: false,
    };
  },
};
</script>
