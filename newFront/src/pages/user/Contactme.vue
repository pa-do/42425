<template>
  <div class="content my-0">
    <div class="container">
      <div class="col-md-10 ml-auto mr-auto">
        <div>
          <h3 class="title text-black my-0 pt-0 pb-4">
            Contact me
            <i class="far fa-edit"></i>
          </h3>
        </div>
        <table class="table table-borderless">
          <tbody>
            <tr>
              <th scope="row">Name</th>
              <td>{{user.name}}</td>
            </tr>
            <tr>
              <th scope="row">Date of birth</th>
              <!-- <td>{{moment(user.birthDate).format("YYYY-MM-DD")}}</td> -->
              <td>{{user.birthDate}}</td>
            </tr>
            <tr>
              <th scope="row">Address</th>
              <td>{{user.address}}</td>
            </tr>
            <tr>
              <th scope="row">Email</th>
              <td>{{user.email}}</td>
            </tr>
            <tr>
              <th scope="row">Phone</th>
              <td>{{user.phone}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';
// import vueMoment from 'vue-moment';

export default {
  name: "contactme",
  bodyClass: "contactme-page",
  components: {},
  created() {
    console.log(this.user.birthDate);
  },
  props: {
    user: Object
  },

};
</script>

<style>
</style>