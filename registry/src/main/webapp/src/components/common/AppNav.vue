<template>
  <md-toolbar>
    <md-button class="md-icon-button" v-show="!hideMenu" @click="toggleSidenav">
      <md-icon>menu</md-icon>
    </md-button>
  
    <h2 class="md-title" style="flex: 1">
      {{ title }}
    </h2>
  
    <md-button class="md-icon-button" v-for="option in options" :key="option.name">
      <md-icon>{{ option.icon }}</md-icon>
    </md-button>
  
  </md-toolbar>
</template>

<script>
import Auth from '@/auth'
export default {
  props: {
    hideMenu: {
      type: Boolean,
      default() { return false; },
    },
    title: {
      type: String,
      default() { return ''; },
    },
    options: {
      type: Array,
      default() { return []; },
    },
  },
  data() {
    return {
      auth: this.$store.state.auth,
      searchText: '',
      searchFlag: '',
      //
      progress: 0,
      progressInterval: null,
      done: false,
      transition: true,
    }
  },
  methods: {
    logout() {
      Auth.logout()
    },
    submitSearch() {
      this.$store.commit(
        'APPNAV_SEARCH',
        {
          searchText: this.searchText,
          searchTimestamp: Date.now()
        }
      )
    },
    toggleSidenav() {
      this.$emit('TOGGLE_SIDEBAR');
    },
  }
}
</script>

<style lang="scss" media="screen" scoped>
/* 
  You can use BEM style even though you are scoped. Helps to reason about
  your styles.
*/

.ev-appnav__logo {
  width: 40px;
}

.ev-appnav__search {
  margin-right: 10px;
}

.ev-appnav__logout {
  margin-top: 1px;
}
</style>