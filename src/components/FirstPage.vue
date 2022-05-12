<template>
  <!-- BODY -->
  <div>
    <!-- SEARCH BAR -->
    <div class="boxmain">
      <div class="InputBox">
        <input
          type="search"
          id="inputbox"
          class="inputbox"
          v-model="city"
          placeholder="Enter city name..."
          autocomplete="off"
          @keyup.enter="getfunction(0, 0)"
          @input="getsearch()"
        />
        <button class="searchbutton">
          <img
            src="/assets/search.png"
            alt="search icon"
            width="20"
            height="20"
            @click="getfunction(0, 0)"
          />
        </button>
      </div>
    
      <div v-show="this.show" class="menu">
        <div
          class="cityname"
          v-for="list in getcity"
          :key="list"
          @click="getkey(list)"
        >
          {{ list }}
        </div>
    
      </div>
    </div>
    <!-- SEARCH BAR -->
    <!-- WEATHER REPORT BODY -->
    <div v-if="getCurrentWeatherReport != null" class="scroll">
      <div>
        <div>
    <!-- CURRENT WEATHER REPORT -->
          <div class="head">
            <div
              class="weatherbox1"
              v-bind:style="{
                'background-image':
                  'url(' + getCurrentWeatherReport.weathergif + ')',
              }"
            >
              <div class="details">
                <div></div>
                <div class="city" id="city">
                  {{ getCurrentWeatherReport.city }}
                </div>
                <div v-if="getCurrentWeatherReport.date === '0-0-0'">
                  <div class="date" id="date">
                    {{ moment(new Date()).format("yyyy-MM-DD") }}
                  </div>
                  <div>
                    {{
                      moment(new Date()).format("yyyy-MM-DD") | moment("ddd")
                    }}
                  </div>
                </div>
                <div v-else>
                  <div class="date" id="date">{{ this.date }}</div>
                  <div class="date">{{ this.date | moment("ddd") }}</div>
                </div>

                <div
                  class="date1"
                  id="date"
                  v-if="
                    getNextHoursWeatherReport.length &&
                    getNextHoursWeatherReport[0].startTime
                  "
                >
                  {{ getNextHoursWeatherReport[0].startTime - 1 }}:00-{{
                    getNextHoursWeatherReport[0].startTime
                  }}:00
                </div>
                <div class="date1" id="date" v-else>23:00-00:00</div>
              </div>
              <div class="status">
                <div class="flex">
                  <img
                    id="hum1"
                    width="15"
                    height="15"
                    src="@/assets/thermometer.png"
                  />
                  <p class="temp" id="temp">
                    {{
                      getCurrentWeatherReport.temperature
                        ? getCurrentWeatherReport.temperature.toFixed(1)
                        : 0
                    }}&#x2103;
                  </p>
                </div>
                <div class="flex">
                  <img
                    id="hum"
                    width="10"
                    height="10"
                    src="@/assets/humidity.png"
                  />
                  <p class="min-max" id="min">
                    {{
                      getCurrentWeatherReport.humidity
                        ? getCurrentWeatherReport.humidity.toFixed(1)
                        : 0
                    }}%
                  </p>
                </div>
                <div class="flex">
                  <img
                    width="20"
                    height="20"
                    src="@/assets/precipitation.png"
                  />
                  <p class="min-max" id="min">
                    {{
                      getCurrentWeatherReport.precipitation
                        ? getCurrentWeatherReport.precipitation.toFixed(1)
                        : 0
                    }}%
                  </p>
                </div>

                <div class="weather" id="weather">
                  {{ getCurrentWeatherReport.weatherType }}
                </div>
              </div>
            </div>
    <!-- CURRENT WEATHER REPORT -->
            <div class="head1">
              <!--WEATHER REPORT FOR TIME INTERVAL -->
              <div
                v-for="interval in getNextHoursWeatherReport"
                :key="interval"
                @click="getfunction(interval.date, interval.startTime)"
              >
                <div class="weatherbox2">
                  <div v-if="interval.weathergif != null">
                    <img
                      id="img1"
                      width="70"
                      height="70"
                      :src="interval.weathericon"
                    />
                  </div>
                  <div v-else>
                    <img
                      id="img1"
                      width="100"
                      height="100"
                      src="https://cdn1.iconfinder.com/data/icons/duotone-weather/24/unknown_weather-512.png"
                    />
                  </div>
                  <div class="details">
                    <div v-if="interval.startTime == 23">
                      <div class="date1" id="date">
                        {{ interval.startTime }}:00-0:00
                      </div>
                    </div>
                    <div v-else>
                      <div class="date1" id="date">
                        {{ interval.startTime }}:00-{{
                          interval.startTime + 1
                        }}:00
                      </div>
                    </div>
                  </div>

                  <div class="status">
                    <div class="flex">
                      <img
                        id="hum1"
                        width="15"
                        height="15"
                        src="@/assets/thermometer.png"
                      />
                      <p class="temp">
                        {{ interval.temperature.toFixed(1) }}&#x2103;
                      </p>
                    </div>
                    <div class="flex">
                      <img
                        id="hum"
                        width="10"
                        height="10"
                        src="@/assets/humidity.png"
                      />
                      <p class="min-max" id="min">
                        {{ interval.humidity.toFixed(1) }}%
                      </p>
                    </div>
                    <div class="flex">
                      <img
                        width="20"
                        height="20"
                        src="@/assets/precipitation.png"
                      />
                      <p class="min-max" id="min">
                        {{ interval.precipitation.toFixed(1) }}%
                      </p>
                    </div>

                    <div class="weather" id="weather">
                      {{ interval.weatherType }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
              <!--WEATHER REPORT FOR TIME INTERVAL</div> -->
          </div>
        </div>
        <div class="head2">
          <!-- WEATHER REPORT FOR NEXT SIX DAYS-->
          <div
            v-for="interval1 in getNextDaysWeatherReport"
            :key="interval1"
            @click="getfunction(interval1.date, 0)"
          >
            <div class="weatherbox">
              <div class="day">{{ interval1.date | moment("ddd") }}</div>
              <div v-if="interval1.weatherimage">
                <img
                  id="img1"
                  width="70"
                  height="70"
                  v-bind:src="
                    interval1.weatherimage
                      ? interval1.weatherimage
                      : 'https://cdn1.iconfinder.com/data/icons/duotone-weather/24/unknown_weather-512.png'
                  "
                />
              </div>
              <div class="details">
                <div class="city" id="city">{{ interval1.city }}</div>
                <div class="date" id="date">{{ interval1.date }}</div>
              </div>
              <div class="details"></div>

              <div class="status">
                <div class="flex">
                  <img
                    id="hum1"
                    width="15"
                    height="15"
                    src="@/assets/thermometer.png"
                  />
                  <p class="temp">
                    {{ interval1.avgTemperature.toFixed(1) }}&#x2103;
                  </p>
                </div>
                <div class="flex">
                  <p class="mm" id="min">
                    {{ interval1.minTemperature.toFixed(1) }}&#x2103;|
                  </p>
                  <p class="mm" id="min">
                    {{ interval1.maxTemperature.toFixed(1) }}&#x2103;
                  </p>
                </div>
                <div class="flex">
                  <img
                    id="hum"
                    width="10"
                    height="10"
                    src="@/assets/humidity.png"
                  />
                  <p class="min-max" id="min">
                    {{ interval1.avgHumidity.toFixed(1) }}%
                  </p>
                </div>
                <div class="flex">
                  <img
                    width="20"
                    height="20"
                    src="@/assets/precipitation.png"
                  />
                  <p class="min-max" id="min">
                    {{ interval1.avgPrecipitation.toFixed(1) }}%
                  </p>
                </div>

                <div class="weather" id="weather">
                  {{ interval1.weatherType }}
                </div>
              </div>
            </div>
          </div>
          <!-- WEATHER REPORT FOR NEXT SIX DAYS-->
        </div>
      </div>
    </div>
    <!-- WEATHER REPORT BODY -->
    <!-- ERROR BODY -->
    <div v-else class="img1">
      <div class="temp">DATA NOT FOUND</div>
      <div>
        <img
          src="https://c.tenor.com/loJefXdup_gAAAAj/sorry-stitch.gif"
          width="450"
          height="250"
        />
      </div>
    </div>
    <!-- ERROR BODY -->
  </div>
  <!-- BODY -->
</template>

<script>
import moment from "moment";
import { mapActions } from "vuex";
import { mapGetters } from "vuex";
export default {
  name: "FirstPage",
  data() {
    return {
      city: "",
      display: true,
      date: "",
      time: 0,
      time1: 0,
      show: false,
      image: "",
      filteredUsers: [],
      search: [],
    };
  },
  computed: {
    ...mapGetters([
      "getCurrentWeatherReport",
      "getNextHoursWeatherReport",
      "getNextDaysWeatherReport",
      "getcity",
    ]),
  },
  created() {
    this.setcity();
    this.city = "bangalore";
    this.time = moment(new Date()).format("HH");
    this.time1 = moment(new Date()).format("HH");
    this.getCurrent(0, 0);
    this.getRange();
    this.getNext();
  },
  methods: {
    ...mapActions([
      "getcurrentreport",
      "gethoursreport",
      "getdaysreport",
      "getcityname",
    ]),

    getsearch() {
      if (this.city == "") this.show = false;
      else this.show = true;
      this.search = this.getcity.filter((post) =>
        post.toLowerCase().includes(this.city.toLowerCase())
      );
    },
    setcity() {
      this.getcityname();
    },
    getkey(city) {
      this.city = city;
      this.show = false;
      this.getfunction(0, 0);
    },
    async getfunction(date, time) {
      this.show = false;
      this.date = date;
      await this.getCurrent(date, time);
      await this.getRange();
      await this.getNext();
    },
    async getCurrent(date, time) {
      if (date == 0 && time == 0) {
        this.date = moment(new Date()).format("yyyy-MM-DD");
        this.time = moment(new Date()).format("HH");
      } else if (time == -1) {
        this.date = date;
        this.time = 0;
      } else {
        this.date = date;
        this.time = time;
      }
      let api = this.city + "/" + this.date + "/" + this.time;
      this.getcurrentreport(api);
      if (this.getCurrentWeatherReport == null) {
        this.display = false;
      } else {
        this.display = true;
      }
    },

    async getRange() {
      this.range = [];
      let api = this.city + "/" + this.date + "/" + this.time;
      this.gethoursreport(api);
      // this.range=this.getNextHoursWeatherReport
    },
    async getNext() {
      this.next = [];
      let api = this.city + "/" + this.date;
      this.getdaysreport(api);
    },
  },
};
</script>

<style>
body {
  background-repeat: no-repeat;
  height: 900px;
  opacity: 2;
  font-size: 20px;
  overflow: hidden;
  background-image: url(@/assets/bg.jpeg);
  background-size: 2300px;
}
</style>