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
              <td>박도희</td>
            </tr>
            <tr>
              <th scope="row">Date of birth</th>
              <td>92.11.22.</td>
            </tr>
            <tr>
              <th scope="row">Address</th>
              <td>{{address}}</td>
            </tr>
            <tr>
              <th scope="row">Email</th>
              <td>{{email}}</td>
            </tr>
            <tr>
              <th scope="row">Phone</th>
              <td>{{phone}}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "contactme",
  bodyClass: "contactme-page",
  components: {},
  mounted() {
    this.getdata();
  },
  methods: {
    getdata() {},
  },
  data: () => {
    return {
      uid: "1",
      address: "경북 구미시",
      phone: "010-4822-3759",
      email: "dohee.pa@gmail.com",
      git: "github.com/do-park",
    };
  },
};
</script>

<style>
</style>