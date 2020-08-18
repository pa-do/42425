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
                    <fg-input
                      v-model="newName"
                      id="newName"
                      placeholder="이름을 입력하세요."
                      type="text"
                      style="width:100%;"
                    ></fg-input>
                    <n-button @click="modifyName" class="btn btn-primary btn-round btn-md mr-1">수정</n-button>
                    <n-button @click="updateName_off" class="btn btn-danger btn-round btn-md">취소</n-button>
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
                  <span v-if="user.birthDate">
                    <td class="date">{{user.birthDate.substring(0,10)}}</td>
                  </span>
                </span>
                <span v-else>
                  <td>
                    <input type="date" v-model="newBD" id="newBD" placeholder="생년월일을 입력하세요." />
                    <n-button
                      @click="modifybirthDate"
                      class="btn btn-primary btn-round btn-md mr-1"
                    >수정</n-button>
                    <n-button
                      @click="updatebirthDate_off"
                      class="btn btn-danger btn-round btn-md"
                    >취소</n-button>
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
                  <fg-input
                    v-model="newAddress"
                    id="newAddress"
                    placeholder="주소를 입력하세요."
                    type="text"
                    style="width:100%;"
                  ></fg-input>
                  <n-button @click="modifyAddress" class="btn btn-primary btn-round btn-md mr-1">수정</n-button>
                  <n-button @click="updateAddress_off" class="btn btn-danger btn-round btn-md">취소</n-button>
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
                  <span v-if="user.phone">
                    <td>{{user.phone.substring(0,3)}} - {{user.phone.substring(3,7)}} - {{user.phone.substring(7,11)}}</td>
                  </span>
                </span>
                <span v-else>
                  <fg-input
                    v-model="newPhone"
                    id="newPhone"
                    placeholder="휴대폰 번호를 '숫자만' 입력하세요."
                    type="text"
                    style="width:100%;"
                  ></fg-input>
                  <n-button @click="modifyPhone" class="btn btn-primary btn-round btn-md mr-1">수정</n-button>
                  <n-button @click="updatePhone_off" class="btn btn-danger btn-round btn-md">취소</n-button>
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
                  <td>
                    <a :href="`${user.website}`" target="_blank">{{user.website}}</a>
                  </td>
                </span>
                <span v-else>
                  <fg-input
                    v-model="newWeb"
                    id="newWeb"
                    placeholder="깃허브 주소를 입력해 주세요."
                    type="text"
                    style="width:100%;"
                  ></fg-input>
                  <n-button @click="modifyWebsite" class="btn btn-primary btn-round btn-md mr-1">수정</n-button>
                  <n-button @click="updateWebsite_off" class="btn btn-danger btn-round btn-md">취소</n-button>
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
                  <td class="date">{{user.birthDate.substring(0,10)}}</td>
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
                  <td>{{user.phone.substring(0,3)}} - {{user.phone.substring(3,7)}} - {{user.phone.substring(7,11)}}</td>
                </tr>
              </span>
              <span v-if="user.website">
                <tr>
                  <th scope="row">Github</th>
                  <td>
                    <a :href="`${user.website}`" target="_blank">{{user.website}}</a>
                  </td>
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
import { FormGroupInput as FgInput, Button } from "@/components";
import { DatePicker } from "element-ui";

export default {
  name: "contactme",
  bodyClass: "contactme-page",
  components: { [DatePicker.name]: DatePicker, FgInput, [Button.name]: Button },
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
      this.newName = this.user.name;
    },
    updateName_off() {
      this.update_name = false;
      this.newName = null;
    },
    modifyName() {
      if (this.newName.length > 10) {
        Swal.fire({
          icon: "warning",
          title: "이름이 너무 깁니다.",
          text: "이름을 10자 이하로 입력하세요.",
        });
        return;
      }
      this.$axios
        .put("/account/modify/name", {
          uid: this.user.uid,
          name: this.newName,
        })
        .then((response) => {
          this.result = response.data;
          this.$session.set("user", response.data.object);
          Swal.fire({
            icon: "success",
            title: "회원정보 수정 성공",
            text: "이름을 성공적으로 수정하였습니다.",
          });
          this.updateName_off();
          this.$emit("update");
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    // 생일 수정
    updatebirthDate_on() {
      this.update_birthDate = true;
      this.newBD = this.user.birthDate;
    },
    updatebirthDate_off() {
      this.update_birthDate = false;
      this.newBD = null;
    },
    modifybirthDate() {
      this.$axios
        .put("/account/modify/birthdate", {
          uid: this.user.uid,
          birthDate: this.newBD,
        })
        .then((response) => {
          this.result = response.data;
          this.$session.set("user", response.data.object);
          Swal.fire({
            icon: "success",
            title: "회원정보 수정 성공",
            text: "생일을 성공적으로 수정하였습니다.",
          });
          this.updatebirthDate_off();
          this.$emit("update");
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    // 주소 수정
    updateAddress_on() {
      this.update_address = true;
      this.newAddress = this.user.address;
    },
    updateAddress_off() {
      this.update_address = false;
      this.newAddress = null;
    },
    modifyAddress() {
      if (this.newAddress.length > 100) {
        Swal.fire({
          icon: "warning",
          title: "주소가 너무 깁니다.",
          text: "주소를 100자 미만으로 입력하세요.",
        });
        return;
      }
      this.$axios
        .put("/account/modify/address", {
          uid: this.user.uid,
          address: this.newAddress,
        })
        .then((response) => {
          this.result = response.data;
          this.$session.set("user", response.data.object);
          Swal.fire({
            icon: "success",
            title: "회원정보 수정 성공",
            text: "주소를 성공적으로 수정하였습니다.",
          });
          this.updateAddress_off();
          this.$emit("update");
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    // 휴대폰 번호 수정
    updatePhone_on() {
      this.update_phone = true;
      this.newPhone = this.user.phone;
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
        Swal.fire({
          icon: "error",
          title: "숫자만 입력해 주세요.",
          text: "한글, 영문, 특수문자 등은 입력하실 수 없습니다.",
        });
        return;
      }
      this.newPhone = this.newPhone.replace(/[^0-9]/g, "");
      if (this.newPhone.length !== 11) {
        Swal.fire({
          icon: "error",
          title: "11자리의 숫자를 입력해 주세요.",
          text: "010을 포함해 11자리의 숫자를 입력하세요.",
        });
        return;
      }
      this.$axios
        .put("/account/modify/phone", {
          uid: this.user.uid,
          phone: this.newPhone,
        })
        .then((response) => {
          this.result = response.data;
          this.$session.set("user", response.data.object);
          Swal.fire({
            icon: "success",
            title: "회원정보 수정 성공",
            text: "휴대폰 번호를 성공적으로 수정하였습니다.",
          });
          this.updatePhone_off();
          this.$emit("update");
        })
        .catch((err) => {
          console.log("Err!!! :", err.response);
        });
    },
    // 깃허브 수정
    updateWebsite_on() {
      this.update_website = true;
      this.newWeb = this.user.website;
    },
    updateWebsite_off() {
      this.update_website = false;
      this.newWeb = null;
    },
    modifyWebsite() {
      if (this.newWeb.length > 200) {
        Swal.fire({
          icon: "warning",
          title: "깃허브 주소가 너무 깁니다.",
          text: "깃허브 주소를 200자 미만으로 입력하세요.",
        });
        return;
      }
      this.$axios
        .put("/account/modify/website", {
          uid: this.user.uid,
          website: this.newWeb,
        })
        .then((response) => {
          this.result = response.data;
          this.$session.set("user", response.data.object);
          Swal.fire({
            icon: "success",
            title: "회원정보 수정 성공",
            text: "깃허브 주소를 성공적으로 수정하였습니다.",
          });
          this.updateWebsite_off();
          this.$emit("update");
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