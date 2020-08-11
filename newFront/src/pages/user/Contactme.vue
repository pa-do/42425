<template>
  <div class="content my-0">
    <div class="container">
      <div class="col-md-10 ml-auto mr-auto">
        <div>
          <h3 class="title text-black my-0 pt-0 pb-4">Contact me</h3>
        </div>
        <span v-if="mine">
          <table class="table table-borderless">
            <tbody>
              <!-- <span v-if="!update_name">  -->
              <tr>
                <th scope="row">
                  Name
                  <span v-if="!update_name">
                    <i class="far fa-edit" @click="updateName_on"></i>
                  </span>
                </th>
                <span v-if="!update_name">
                  <td>{{user.name}}</td>
                </span>
                <span v-else>
                  <td>
                    <textarea
                      class="form-control"
                      v-model="newName"
                      id="newName"
                      placeholder="이름을 입력하세요."
                      type="text"
                      style="width:100%;"
                    />
                    <button @click="modifyName" class="btn btn-primary btn-round btn-md mr-1">수정</button>
                    <button @click="updateName_off" class="btn btn-danger btn-round btn-md">취소</button>
                  </td>
                </span>
              </tr>

              <tr>
                <th scope="row">
                  Date of birth
                  <span v-if="!update_birthDate">
                    <i @click="updatebirthDate_on" class="far fa-edit"></i>
                  </span>
                </th>
                <span v-if="!update_birthDate">
                  <td class="date">{{user.birthDate.split("T")[0]}}</td>
                </span>
                <span v-else>
                  <td>
                    <textarea
                      class="form-control"
                      v-model="newBD"
                      id="newBD"
                      placeholder="생일을 'yyyy-mm-dd'의 형태로 입력하세요."
                      type="text"
                      style="width:100%;"
                    />
                    <button
                      @click="modifybirthDate"
                      class="btn btn-primary btn-round btn-md mr-1"
                    >수정</button>
                    <button @click="updatebirthDate_off" class="btn btn-danger btn-round btn-md">취소</button>
                  </td>
                </span>
              </tr>
              <tr>
                <th scope="row">
                  Address
                  <span v-if="!update_address">
                    <i @click="updateAddress_on" class="far fa-edit"></i>
                  </span>
                </th>
                <span v-if="!update_address">
                  <td>{{user.address}}</td>
                </span>
                <span v-else>
                  <textarea
                    class="form-control"
                    v-model="newAddress"
                    id="newAddress"
                    placeholder="주소를 입력하세요."
                    type="text"
                    style="width:100%;"
                  />
                  <button @click="modifyAddress" class="btn btn-primary btn-round btn-md mr-1">수정</button>
                  <button @click="updateAddress_off" class="btn btn-danger btn-round btn-md">취소</button>
                </span>
              </tr>
              <tr>
                <th scope="row">Email</th>
                <td>{{user.email}}</td>
              </tr>
              <tr>
                <th scope="row">
                  Phone
                  <span v-if="!update_phone">
                    <i @click="updatePhone_on" class="far fa-edit"></i>
                  </span>
                </th>
                <span v-if="!update_phone">
                  <td>{{user.phone}}</td>
                </span>
                <span v-else>
                  <textarea
                    class="form-control"
                    v-model="newPhone"
                    id="newPhone"
                    placeholder="휴대폰 번호를 '숫자만' 입력하세요."
                    type="text"
                    style="width:100%;"
                  />
                  <button @click="modifyPhone" class="btn btn-primary btn-round btn-md mr-1">수정</button>
                  <button @click="updatePhone_off" class="btn btn-danger btn-round btn-md">취소</button>
                </span>
              </tr>
              <tr>
                <th scope="row">
                  Github
                  <span v-if="!update_website">
                    <i @click="updateWebsite_on" class="far fa-edit"></i>
                  </span>
                </th>
                <span v-if="!update_website">
                  <td>{{user.website}}</td>
                </span>
                <span v-else>
                  <textarea
                    class="form-control"
                    v-model="newWeb"
                    id="newWeb"
                    placeholder="깃허브 주소를 입력해 주세요."
                    type="text"
                    style="width:100%;"
                  />
                  <button @click="modifyWebsite" class="btn btn-primary btn-round btn-md mr-1">수정</button>
                  <button @click="updateWebsite_off" class="btn btn-danger btn-round btn-md">취소</button>
                </span>
              </tr>
            </tbody>
          </table>
        </span>
        <span v-else>
          <table class="table table-borderless">
            <tbody>
              <span v-if="user.name">
                <tr>
                  <th scope="row">Name</th>
                  <td>{{user.name}}</td>
                </tr>
              </span>
              <span v-if="user.birthDate">
                <tr>
                  <th scope="row">Date of birth</th>

                  <td class="date">{{user.birthDate.split("T")[0]}}</td>
                </tr>
              </span>
              <span v-if="user.address">
                <tr>
                  <th scope="row">Address</th>
                  <td>{{user.address}}</td>
                </tr>
              </span>
              <span v-if="user.email">
                <tr>
                  <th scope="row">Email</th>
                  <td>{{user.email}}</td>
                </tr>
              </span>
              <span v-if="user.phone">
                <tr>
                  <th scope="row">Phone</th>
                  <td>{{user.phone}}</td>
                </tr>
              </span>
              <span v-if="user.website">
                <tr>
                  <th scope="row">Github</th>
                  <td>{{user.website}}</td>
                </tr>
              </span>
            </tbody>
          </table>
        </span>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
// import vueMoment from 'vue-moment';

export default {
  name: "contactme",
  bodyClass: "contactme-page",
  components: {},
  props: {
    user: Object,
    mine: Boolean,
  },
  data: () => {
    return {
      uid: null,

      update_name: false,
      update_birthDate: false,
      update_address: false,
      update_phone: false,
      update_website: false,

      newName: null,
      newBD: null,
      newAddress: null,
      newPhone: null,
      newWeb: null,
    };
  },
  methods: {
    // 이름 수정
    updateName_on() {
      this.update_name = true;
    },
    updateName_off() {
      this.update_name = false;
      this.newName = null;
    },
    modifyName() {
      axios
        .put("http://localhost:8080/account/modify/name", {
          uid: this.user.uid,
          name: this.newName,
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
    },
    // 생일 수정
    updatebirthDate_on() {
      this.update_birthDate = true;
    },
    updatebirthDate_off() {
      this.update_birthDate = false;
      this.newBD = null;
    },
    validBD: function (bd) {
      var re = /^([1-2][0-9][0-9][0-9])-([0-1][0-9])-([0-3][0-9])$/;
      return re.test(bd);
    },
    modifybirthDate() {
      if (!this.validBD(this.newBD)) {
        alert("yyyy-mm-dd의 형태로 입력해 주세요.");
        document.getElementById("newBD").focus();
        return;
      }
      axios
        .put("http://localhost:8080/account/modify/birthdate", {
          uid: this.user.uid,
          birthDate: this.newBD,
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
    },
    // 주소 수정
    updateAddress_on() {
      this.update_address = true;
    },
    updateAddress_off() {
      this.update_address = false;
      this.newAddress = null;
    },
    modifyAddress() {
      axios
        .put("http://localhost:8080/account/modify/address", {
          uid: this.user.uid,
          address: this.newAddress,
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
    },
    // 휴대폰 번호 수정
    updatePhone_on() {
      this.update_phone = true;
    },
    updatePhone_off() {
      this.update_phone = false;
      this.newPhone = null;
    },
    validPhone: function (phone) {
      var re = /^[0-9]/g;
      return re.test(phone);
    },
    modifyPhone() {
      if (!this.validPhone(this.newPhone)) {
        alert("숫자만 입력해 주세요.");
        document.getElementById("newPhone").focus();
        return;
      }
      axios
        .put("http://localhost:8080/account/modify/phone", {
          uid: this.user.uid,
          phone: this.newPhone.replace(/[^0-9]/g, ""),
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
    },
    // 깃허브 수정
    updateWebsite_on() {
      this.update_website = true;
    },
    updateWebsite_off() {
      this.update_website = false;
      this.newWeb = null;
    },
    modifyWebsite() {
      axios
        .put("http://localhost:8080/account/modify/website", {
          uid: this.user.uid,
          website: this.newWeb,
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
    },
  },
};
</script>

<style>
</style>