<template>
  <iframe id="iframe3_id" :src="$route.query.src" width="100%"></iframe>
</template>

<script>
  export default {
    name: 'IframeContent3',
    mounted() {
      let iframe = document.getElementById("iframe3_id");
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

