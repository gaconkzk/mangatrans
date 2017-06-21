<template lang="html">
  <div class="Login__Wrapper">
    <md-card class="Login__CardWrappper">
      <md-card-media class="Login__CardImage">
        <img src="../../assets/img/huge-fly.png" alt="People">
      </md-card-media>

      <md-card-content>
        <md-input-container :class="{'md-input-invalid': errors.has('email')}">
          <label>Email</label>
          <md-input type="email"
            name="email"
            v-model="user"
            v-validate
            data-vv-name="email"
            data-vv-rules="required|email"></md-input>
          <span class="md-error">{{emailError}}</span>
        </md-input-container>

        <md-input-container>
          <label>Password</label>
          <md-input type="password"
            v-model="password"></md-input>
        </md-input-container>

      </md-card-content>

      <md-card-actions>
        <md-button>Register</md-button>
        <md-button class="md-raised md-primary"
          @click.stop="login">
          Login
        </md-button>
      </md-card-actions>
    </md-card>
  </div>
</template>

<script type="text/javascript">

  export default {
    data() {
      return {
        user: '',
        password: '',
      };
    },
    components: {
    },
    computed: {
      emailError() {
        // Exemplo de computed properties
        return (this.errors) ? this.errors.first('email') : '';
      },
    },
    methods: {
      login() {
        this.$router.push({ path: '/products' });
      },
    },
  };
</script>

<style lang="scss" scoped>
  .Login__Wrapper {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  .Login__CardWrappper {
    margin-top: 20vh;
    min-width: 300px;
    width: 22%;
  }

  .Login__CardImage {
    display: flex;
    justify-content: center;
    img {
      max-width: 200px;
    }
  }
</style>
