<template>
  <div class="container">
    <span class="txt1">www.codepen.io/</span>
    <span class="txt2">creativeocean</span>
    <div class="bar"></div>
  </div>
</template>
<script>
import gsap from "gsap";
var t2 = new SplitText(".txt2").chars,
  color2 = "#17c0fd",
  color1 = "#fff",
  moveBar = () => {
    gsap.set(".bar", { left: gsap.getProperty(".txt1", "width") + 1 });
  };
</script>
<script>
export default {
  mounted: function() {
    gsap
      .timeline({ delay: 0.2 })
      .set(".txt1", { color: color1, fontWeight: "regular" })
      .set(".txt2", {
        color: color2,
        fontWeight: "bold",
        opacity: 0,
        x: gsap.getProperty(".txt1", "width") - 2,
        immediateRender: true,
      })
      .set(".bar", { left: 1, backgroundColor: color1, immediateRender: true })

      .to(
        ".bar",
        {
          duration: 0.1,
          opacity: 0,
          ease: Expo.easeIn,
          yoyo: true,
          repeat: 5,
          repeatDelay: 0.3,
        },
        0
      )
      .from(
        ".txt1",
        {
          duration: 1.1,
          width: 0,
          ease: SteppedEase.config(14),
          onUpdate: moveBar,
        },
        2.5
      )
      .to(".bar", { duration: 0.05, backgroundColor: color2 }, "+=0.15")
      .to(
        ".bar",
        { duration: 1.0, width: 290, ease: Power4.easeInOut },
        "+=0.1"
      )
      .from(
        ".container",
        { duration: 1.0, x: 135, ease: Power4.easeInOut },
        "-=1.0"
      )
      .to(".txt2", { duration: 0.01, opacity: 1 }, "-=0.1")
      .to(".bar", { duration: 0.4, x: 290, width: 0, ease: Power4.easeIn })
      .from(
        t2,
        { duration: 0.6, opacity: 0, ease: Power3.easeInOut, stagger: 0.02 },
        "-=0.5"
      )
      .to(
        ".txt1",
        { duration: 1.5, opacity: 0.25, ease: Power3.easeInOut },
        "-=1.2"
      )
      .timeScale(1.45);
  },
};
</script>

<style scoped>
html,
body {
  width: 100%;
  height: 100%;
  background: #000;
  position: relative;
  overflow: hidden;
}
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
