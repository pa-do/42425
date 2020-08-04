<template>
  <div class="user" id="join">
    <div class="wrapC table">
      <div class="middle">
        <h1>비밀번호 찾기</h1>
        <div class="form-wrap">
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
          </div>

          <div class="input-wrap password-wrap">
            <input
              v-model="passwordConfirm"
              id="password-confirm"
              :type="passwordConfirmType"
              placeholder="비밀번호를 한번 더 입력해주세요"
            />
          </div>
        </div>
        <button class="btn" @click="join">
          <span>수정 완료</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: Modifypw,
};
</script>

<style>
</style>