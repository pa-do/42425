<template>
    <div class="container">
      <div class="row justify-content-center pb-5">
        <div class="col-md-12 heading-section text-center ftco-animate">
          <!-- <h1 class="big big-2">Skills</h1> -->
          <h2 class="mb-4">My Skills</h2>
          <!-- <p>Far far away, behind the word mountains, far from the countries Vokalia and Consonantia</p> -->
          <i class="far fa-edit" @click="updateSkills" v-if="!modifyMode"></i>
          <button class="btn btn-primary" v-if="modifyMode" @click="modifyOk">완료 </button>
        </div>
      </div>
      <div class="row">
        <div class="col-md-6 animate-box" v-for="(skill,index) in myskill" :key="index" >
          <!-- 수정 모드 아닐 경우 -->
          <div class="progress-wrap ftco-animate" v-if="!modifyMode"> 
            <h3>{{ skill.skill }}</h3>
            <div class="progress">
              <div class="progress-bar btn-primary" role="progressbar" :aria-valuenow="skill.value"
                aria-valuemin="0" aria-valuemax="100" :style="'width:' + skill.value + '%'">
                <span>{{ skill.value }}%</span>
                </div>
            </div>
          </div>
          <!-- 수정 모드일 경우 -->
          <div class="progress-wrap ftco-animate" v-if="modifyMode"> 
            <button class="btn btn-danger" @click="delSkill(skill.uid, skill.skill)">삭제</button>

          </div>
        </div>
      </div>
    </div>
</template>

<script>
import axios from 'axios';
import Progress from '../components/Progress' 

export default {
    components:{
      [Progress.name]: Progress,
    },
    data: () => {
      return {
        myskill: [],
        modifyMode: false,
      };
    },
    created(){
      this.getSkills();
    },
    updated(){
      this.getSkills();
    },
    methods: {
      getSkills(){
         axios
        .get(`http://localhost:8080/portfolio/skill/${this.$session.get("user").uid}`)
        .then((data) => {
            this.myskill = data.data.object; 
        });
      },
      updateSkills(){
        this.modifyMode = true;
      },
      delSkill(uid, skill){
        axios
        .delete(`http://localhost:8080/portfolio/skill/delete/${uid}/${skill}`)
        .then((data) => {
          console.log(data);
        })
        .catch((error) => {
          console.log("왜에러??")
          console.log(error)
        })
      },
      modifyOk(){
        this.modifyMode = false;
      }
    }
}
</script>

<style>
</style>