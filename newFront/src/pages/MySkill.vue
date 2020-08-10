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
            <input class="input" v-model="skill.skill" :placeholder="skill.skill" />
            <button class="btn btn-danger" @click="delSkill(skill.uid, skill.skill)">삭제</button>
            <v-slider v-model="skill.value"
                      thumb-label="always"
                      thumb-color="rgb(51,75,85)">
            </v-slider>
           
          </div>
          
        </div>
        <div> 
          <button class="btn btn-info" @click="addSkill">skill 추가</button>
        </div>
      </div>
    </div>
</template>


<script>
import axios from 'axios';
import Progress from '../components/Progress' 

const path = "http://localhost:8080/portfolio"

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
    mounted(){
      this.getSkills();
    },
    methods: {
      getSkills(){
         axios
        .get(path + `/skill/${this.$session.get("user").uid}`)
        .then((data) => {
            this.myskill = data.data.object; 
        });
      },
      updateSkills(){
        console.log("?");
        this.modifyMode = true;
      },
      delSkill(uid, skill){
        axios
        .delete(path + `/skill/delete/${uid}/${skill}`)
        .then((data) => {
          console.log(data);
        })
        .catch((error) => {
          console.log(error)
        })
      },
      addSkill(){
        // Swal.fire({
        //   title: 'Skill 추가',
        //   input: 'text',
        //   input: 'text',
        //   inputAttributes: {
        //     autocapitalize: 'off'
        //   },
        //   showCancelButton: true,
        //   confirmButtonText: 'Look up',
        //   showLoaderOnConfirm: true,
        //   preConfirm: (login) => {
        //     return fetch(`//api.github.com/users/${login}`)
        //       .then(response => {
        //         if (!response.ok) {
        //           throw new Error(response.statusText)
        //         }
        //         return response.json()
        //       })
        //       .catch(error => {
        //         Swal.showValidationMessage(
        //           `Request failed: ${error}`
        //         )
        //       })
        //   },
        //   allowOutsideClick: () => !Swal.isLoading()
        // }).then((result) => {
        //   if (result.value) {
        //     Swal.fire({
        //       title: `${result.value.login}'s avatar`,
        //       imageUrl: result.value.avatar_url
        //     })
        //   }
        // })
      },
      modifyOk(){
        axios
        .put(path + `/skill/modify`,{
            myskills : this.myskill,
        })
        .then((data) => {
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