<template>
  <iframe id="iframe2_id" :src="$route.query.src" width="100%"></iframe>
</template>

<script>
  export default {
    name: 'IframeContent2',
    mounted() {
      let iframe = document.getElementById("iframe2_id");
      let clientHeight = document.documentElement.scrollHeight;
      let iframeOffsetTop = iframe.offsetTop;
      iframe.style.height = (clientHeight - iframeOffsetTop - 110) + 'px';
    }
  }

</script>

<style scoped>

  iframe {
    border: none;
  }

</style>

