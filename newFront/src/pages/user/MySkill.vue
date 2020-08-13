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
            <h3>{{ skill.sid }}</h3> 
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
            <div class="row">
              <div class="col-sm-6 col-lg-3">
                <fg-input :placeholder="skill.skill" v-model="skill.skill"></fg-input>
              </div>
              <div class="col-sm-6 col-lg-3">
                <fg-input :placeholder="skill.value" v-model="skill.value"></fg-input>
              </div>
              <button class="btn btn-danger" @click="delSkill(skill.uid, skill.skill)">삭제</button>
            </div>
          </div>
        </div>
        <div> 
          <button class="btn btn-info" @click="addSkill" v-if="modifyMode">skill 추가</button>
        </div>
      </div>
    </div>
</template>


<script>
import FormGroupInput from '../../components/Inputs/formGroupInput' 

export default {
    components:{
      [FormGroupInput.name]: FormGroupInput
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
    mounted(){
      this.getSkills();
    },
    methods: {
      getSkills(){
        this.$axios
        .get(`/portfolio/skill/${this.$session.get("user").uid}`)
        .then((data) => {
            this.myskill = data.data.object; 
        });
      },
      updateSkills(){
        this.modifyMode = true;
      },
      delSkill(uid, skill){
        this.$axios
        .delete(`/portfolio/skill/delete/${uid}/${skill}`)
        .then((data) => {
          console.log(data);
        })
        .catch((error) => {
          console.log(error)
        })
      },
      addSkill(){
        axios
        .post(path + "/skill/create",{
          uid : this.$session.get("user").uid
        })
        .then((data) => {
          console.log(this.myskill);
          this.getSkills();
        })
        .catch((err) => console.log(err));
      },
      modifyOk(){
        this.$axios
        .put(`/portfolio/skill/modify`,{
            myskills : this.myskill,
        })
        .then((data) => {
          console.log("성공 !!!ㄴ");
          console.log(data);
        })
        .catch((error) => {
          console.log("error");
          console.log(error);
        })


        this.modifyMode = false;

      }
    }
}
</script>

<style>
</style>