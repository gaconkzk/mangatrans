import firebase from 'firebase'

// Initialize Firebase
const config = {
  apiKey: 'AIzaSyA4CTbLbO0OEAFtwHK4GjeD_QYNsCDh73o',
  authDomain: 'theflies-cff90.firebaseapp.com',
  databaseURL: 'https://theflies-cff90.firebaseio.com',
  projectId: 'theflies-cff90',
  storageBucket: 'theflies-cff90.appspot.com',
  messagingSenderId: '499171312828'
}
firebase.initializeApp(config)

export default {
  database: firebase.database()
}
