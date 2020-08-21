<template>
  <footer
    class="footer"
    :class="{ [`footer-${type}`]: type }"
    :data-background-color="backgroundColor"
  >
    <div class="container">
      <nav>
        <ul>
          <li>
            <a href="https://www.creative-tim.com">Creative Tim</a>
          </li>
          <li>
            <a href="https:///presentation.creative-tim.com">About Us</a>
          </li>
          <li>
            <a href="https:///blog.creative-tim.com">Blog</a>
          </li>
        </ul>
      </nav>
      <div class="copyright">
        &copy; {{ year }}, 지렸조 팀장
        <a href target="_blank" rel="noopener">한정훈</a>. 팀원
        <a href target="_blank" rel="noopener">박도희</a>.
        <a href target="_blank" rel="noopener">허성수</a>.
        <a href target="_blank" rel="noopener">정성훈</a>. and
        <a href target="_blank" rel="noopener">김소희</a>.
      </div>
    </div>
  </footer>
</template>
<script>
export default {
  props: {
    backgroundColor: String,
    type: String,
  },
  data() {
    return {
      year: new Date().getFullYear(),
    };
  },
};
</script>
<style></style>
