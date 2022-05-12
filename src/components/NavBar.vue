<template>
  <div class="navbar">
    <div class="heading2">
      <div>
        <img
          class="back"
          src="/assets/back.webp"
          width="40"
          height="40"
          @click="adddata(1)"
        />
        <img src="/assets/weatherlens.webp" width="50" height="50" />
      </div>
      <div class="heading">Weather Lens</div>
    </div>
    <div class="heading1" v-if="time1 >= 0 && time1 < 12">
      <div>
        <img src="/assets/gm.webp" width="50" height="50" />
      </div>
      <div>Good Morning</div>
    </div>
    <div class="heading1" v-else-if="time1 >= 12 && time1 < 16">
      <div>
        <img src="/assets/ga.png" width="50" height="50" />
      </div>

      <div>Good Afternoon</div>
    </div>
    <div class="heading1" v-else-if="time1 >= 16 && time1 < 19">
      <div>
        <img src="/assets/ge.png" width="50" height="50" />
      </div>

      <div>Good Evening</div>
    </div>
    <div class="heading1" v-else>
      <div>
        <img src="/assets/gn.png" width="50" height="50" />
      </div>

      <div>Good Night</div>
    </div>
    <div class="heading3">
      <img src="/assets/add.png" width="55" height="60" @click="adddata(2)" />
    </div>
  </div>
</template>
<script>
import moment from "moment";
export default {
  data() {
    return {
      time1: 0,
    };
  },
  created() {
    this.time1 = moment(new Date()).format("HH");
  },
  methods: {
    adddata(i) {
      if (i == 2) this.$router.push("/Form");
      else if (i == 1) this.$router.go(-1);
    },
  },
};
</script>
<style>
</style>