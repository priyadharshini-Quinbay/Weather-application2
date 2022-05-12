<template>
  <div>
    <div class="signup-form">
      <div class="form-header">
        <h1>ENTER HOURLY DATA</h1>
      </div>
      <div class="form-body">
        <div class="horizontal-group">
          <h5>{{ this.errors }}</h5>
          <h4>{{ this.info }}</h4>
          <div class="form-group left">
            <label for="firstname" class="label-title">CITY *</label>
            <input
              type="search"
              id="firstname"
              class="form-input"
              placeholder="Enter your City name"
              required="required"
              v-model="city"
              autocomplete="off"
              @input="getsearch()"
            />
            <div v-show="this.show" class="menu">
              <div
                class="cityname"
                v-for="list in search"
                :key="list"
                @click="getkey(list)"
              >
                {{ list }}
              </div>
            </div>
          </div>
          <div class="form-group right">
            <label for="date" class="label-title"> DATE *</label>
            <input
              type="date"
              id="date"
              class="form-input"
              placeholder="YYYY/MM/DD"
              required="required"
              v-model="date"
              autocomplete="off"
            />
          </div>
          <div class="form-group left">
            <label for="firstname" class="label-title">START TIME *</label>
            <input
              type="number"
              id="firstname"
              class="form-input"
              placeholder="Enter your Start time"
              required="required"
              v-model="time"
              autocomplete="off"
            />
          </div>
          <div class="form-group right">
            <label for="lastname" class="label-title">HUMIDITY % *</label>
            <input
              type="number"
              id="lastname"
              class="form-input"
              placeholder="Humidity in %"
              v-model="humidity"
              autocomplete="off"
            />
          </div>
          <div class="form-group left">
            <label for="firstname" class="label-title">PRECIPITATION % *</label>
            <input
              type="number"
              id="firstname"
              class="form-input"
              placeholder="Precipitation in %"
              required="required"
              v-model="precipitation"
              autocomplete="off"
            />
          </div>
          <div class="form-group right">
            <label for="lastname" class="label-title">TEMPERATURE C *</label>
            <input
              type="number"
              id="lastname"
              class="form-input"
              placeholder="Temperature in C"
              v-model="temperature"
              autocomplete="off"
            />
          </div>
        </div>
      </div>
      <div class="form-footer">
        <div @click="postData(1)" class="btn">ADD</div>
        <br />
        <div @click="postData(2)" class="btn">UPDATE</div>
        <br />
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions } from "vuex";
import { mapGetters } from "vuex";
export default {
  name: "FormPage",
  data() {
    return {
      city: "",
      time: "",
      precipitation: "",
      date: "",
      humidity: "",
      temperature: "",
      errors: "",
      info: "",
      show: false,
      search:[]
    };
  },
  computed: {
    ...mapGetters(["getcity"]),
  },
  created() {
    this.setcity();
  },
  methods: {
    ...mapActions(["getcityname"]),
    setcity() {
      this.getcityname();
    },
    getsearch() {
      if (this.city == "") this.show = false;
      else this.show = true;
      this.search = this.getcity.filter((post) =>
        post.toLowerCase().includes(this.city.toLowerCase())
      );
    },
    getkey(city) {
      this.city = city;
      this.show = false;
    },
    postData(i) {
      this.errors = "";
      this.info = "";
      if (!this.city) {
        this.errors = "Please enter your city";
      } else if (!/[a-zA-Z0-9\s]/.test(this.city)) {
        this.errors = "Please enter valid city";
      } else if (!this.date) {
        this.errors = "Please enter your date";
      } else if (!this.time && this.time >= 0 && this.time <= 23) {
        this.errors = "Please re-enter your time";
      } else if (!this.humidity && this.humidity < 0 && this.humidity > 100) {
        this.errors = "Please re-enter correct humidity";
      } else if (!this.precipitation) {
        this.errors = "Please enter  precipitation";
      } else if (this.precipitation < 0 && this.precipitation > 100) {
        this.errors = "Please re-enter correct precipitation";
      } else if (!this.temperature && this.temperature != 0) {
        this.errors = "Please enter correct temperature";
      }

      if (this.errors.length == 0) {
        if (i == 1) {
          this.axios
            .post(
              `http://localhost:8082//weather-application/weather-report/add-hourly-data/`,
              {
                city: this.city,
                date: this.date,
                startTime: this.time,
                humidity: this.humidity,
                precipitation: this.precipitation,
                temperature: this.temperature,
              }
            )
            .then((result) => {
              if (result.data.result == true) {
                this.info = "ADD SUCCESSFULL";
              } else {
                this.info = "";
                this.errors = result.data.errorMessage;
              }
            });
        } else {
          this.axios
            .put(
              `http://localhost:8082//weather-application/weather-report/update-hourly-data/`,
              {
                city: this.city,
                date: this.date,
                startTime: this.time,
                humidity: this.humidity,
                precipitation: this.precipitation,
                temperature: this.temperature,
              }
            )
            .then((result) => {
              if (result.data.result == true) {
                this.info = "UPDATE SUCCESSFULL";
              } else {
                this.info = "";
                this.errors = result.data.errorMessage;
              }
            });
        }
      }
    },
  },
};
</script>
<style scoped>
*{
  font-family: 'Times New Roman', Times, serif;
  font-size: 15px;
  
}
.signup-form {
  font-family: sans-serif;
  width: 600px;
  margin: 30px auto;
  border-radius: 10px;
}
.form-header {
  /* background-color: #eff0f1; */
  background-image: linear-gradient(
    to right,
    rgb(80, 119, 227),
    rgb(202, 154, 227)
  );
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}
.form-header h1 {
  font-size: 30px;
  text-align: center;
  /* color: #666; */
  color: rgb(207, 198, 242);

  padding: 20px 0;
  border-bottom: 1px solid #cccccc;
  text-shadow: -1px -1px 0 #000, 1px -1px 0 #000, -1px 1px 0 #000,
    1px 1px 0 #000;
}
.form-body {
  padding: 10px 40px;
  text-align: left;
  background-color: white;
  height: 420px;
  margin-top: -20px;
}
.form-body h5 {
  color: red;
  font-weight: bold;
}
.form-body h4 {
  color: green;
  font-weight: bold;
}
.form-group {
  margin-bottom: 20px;
  margin-left: 5px;
}
.form-body .label-title {
  color: #691ce4;
  font-size: 17px;
  text-align: left;
}
.form-body .form-input {
  font-size: 17px;
  box-sizing: border-box;
  width: 100%;
  height: 34px;
  padding-left: 10px;
  padding-right: 10px;
  color: #333333;
  text-align: left;
  border: 1px solid #d6d6d6;
  border-radius: 4px;
  background: white;
  outline: none;
}
.horizontal-group .left {
  float: left;
  width: 49%;
}
.horizontal-group .right {
  float: right;
  width: 49%;
}
input[type="file"] {
  outline: none;
  cursor: pointer;
  font-size: 17px;
}

::-webkit-input-placeholder {
  color: black;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}
.form-footer {
  clear: both;
}

.signup-form {
  box-shadow: 0 2px 4px rgb(0 0 0 / 10%), 0 8px 16px rgb(0 0 0 / 10%);
  box-sizing: border-box;
}

.signup-form .form-footer {
  background-color: #eff0f1;
  border-bottom-left-radius: 10px;
  border-bottom-right-radius: 10px;
  padding: 10px 40px;
  text-align: center;
  border-top: 1px solid #cccccc;
  background-image: linear-gradient(
    to right,
    rgb(80, 119, 227),
    rgb(202, 154, 227)
  );
}
@media only screen and (min-device-width: 150px) and (max-device-width: 300px) {
  .signup-form {
    display: flex;
    flex-direction: column;
    /* align-items: center; */
    width: 550px;
    padding: 100px;
  }
}
@media only screen and (min-device-width: 300px) and (max-device-width: 650px) {
  .signup-form {
    display: flex;
    flex-direction: column;
    width: 850px;
    padding-left: 250px;
  }
}

select {
  size: 400px;
  height: 34px;
  width: 100%;
  border-radius: 4px;
  text-align: center;
  font-size: 17px;
  border: 1px solid #d6d6d6;
}

.interests {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

#choose-file {
  border: none;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  font-size: 17px;
  border: none;

  border-radius: 5px;
  color: black;
  cursor: pointer;
  /* width: 200px; */
  font-weight: bold;
  /* color: rgb(207, 198, 242); */
}

.btn:hover {
  background: #e4f6e06b;
  color: black;
}
.menu {
  width: 50px;
}
</style>
