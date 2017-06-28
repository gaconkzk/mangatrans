<template>
  <div id="app">
    <!--<app-nav></app-nav>-->
    <transition name="slide-fade" mode="out-in">
      <router-view></router-view>
    </transition>
    <!--<div class="container app-content">-->
    <!--<router-view></router-view>-->
    <!--</div>-->
    <!--<app-footer></app-footer>-->
  </div>
</template>

<script>
// import AppNav from './AppNav.vue'
// import AppFooter from './AppFooter.vue'
export default {
  name: 'app',
  components: { 
    // AppNav, AppFooter 
  },
  data() {
    return {
    }
  }
}
</script>

<style lang="scss">
.slide-fade-enter-active {
  transition: all .2s ease;
}

.slide-fade-leave-active {
  transition: all .2s cubic-bezier(1.0, 0.5, 0.8, 1.0);
}

.slide-fade-enter,
.slide-fade-leave-active {
  transform: translateX(10px);
  opacity: 0;
}
</style>