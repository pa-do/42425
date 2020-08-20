<template>
  <div class="container" style="background:#000">
    <span class="txt1">Team.OJO/</span>
    <span class="txt2"> 포 투 포 이 오</span>
    <div class="bar"></div>
  </div>
</template>
<script>
export default {
  mounted: function() {
    require("../assets/js/introscript.js");
  },
};
</script>

<style scoped>
.container {
  font-size: 40px;
  font-family: "Red Hat Display", sans-serif;
  letter-spacing: 1.5px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 600px;
  height: 40px;
}

.container * {
  display: inline-block;
  overflow: hidden;
  position: absolute;
}

.bar {
  width: 3px;
  height: 49px;
  position: absolute;
  top: -1px;
}
</style>
