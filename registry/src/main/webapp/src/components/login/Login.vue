<template>
  <div class="Login__Wrapper">
    <md-card class="Login__CardWrappper">
      <md-card-media class="Login__CardImage">
        <img src="../../assets/images/huge-fly.png" alt="TheFlies">
      </md-card-media>
  
      <md-card-content>
        <md-input-container :class="{'md-input-invalid': error?true:false}">
          <label>Username</label>
          <md-input type="text" id="login.username" v-model="credentials.username"></md-input>
          <span class="md-error">{{error}}</span>
        </md-input-container>
  
        <md-input-container>
          <label>Password</label>
          <md-input type="password" v-model="credentials.password"></md-input>
        </md-input-container>
  
      </md-card-content>
  
      <md-card-actions>
        <md-button>Sign up</md-button>
        <md-button class="md-raised md-primary" @click.stop="submit">
          Login
        </md-button>
      </md-card-actions>
    </md-card>
  
    <spinner v-show="loggingIn" message="Logging in..."></spinner>
    <!--<p>username:
          <strong>demouser</strong>
          <br> password:
          <strong>testpass</strong>
        </p>
        <div class="alert alert-danger" v-if="error">
          <p>{{ error }}</p>
        </div>
        <div class="form-group">
          <input type="text" data-id="login.username" class="form-control js-login__username" placeholder="Enter your username" v-model="credentials.username">
        </div>
        <div class="form-group">
          <input type="password" class="form-control js-login__password " placeholder="Enter your password" v-model="credentials.password">
        </div>
        <button data-id="login.submit" class="btn btn-primary solid blank js-login__submit" @click="submit()">
          Login &nbsp;
          <i class="fa fa-arrow-circle-o-right"></i>
        </button>
        <br>
        <br>
        <br>
        <a href="#">Forgot your password?</a>
        <br> Don’t have an account? &nbsp;
        <a href="#">Sign up here.</a>
      -->
  </div>
</template>

<script>
import Spinner from '@/components/common/Spinner'
export default {
  name: 'login',
  components: { Spinner },
  data() {
    return {
      credentials: {
        username: '',
        password: ''
      },
      loggingIn: false,
      error: ''
    }
  },
  methods: {
    submit() {
      this.loggingIn = true
      const credentials = {
        username: this.credentials.username,
        password: this.credentials.password
      }
      // Auth.login() returns a promise. A redirect will happen on success.
      // For errors, use .then() to capture the response to output
      // error_description (if exists) as shown below:
      this.$auth.login(credentials, 'dashboard').then((response) => {
        this.loggingIn = false
        this.error = utils.getError(response)
      })
    }
  }
}
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