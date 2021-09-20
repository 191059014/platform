<template>
  <iframe :src="$route.query.src" width="100%" :height="frameHeight"></iframe>
</template>

<script>
  export default {
    name: 'Website1',
    data() {
      return {
        frameHeight: ''
      }
    },
    mounted() {
      this.frameHeight = document.documentElement.scrollHeight + 'px';
    }
  }

</script>

<style scoped>

  iframe {
    border: none;
  }

</style>

