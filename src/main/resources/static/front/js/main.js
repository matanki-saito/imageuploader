const awaitForLoad = target => {
  return new Promise(resolve => { // 処理A
    const listener = resolve;     // 処理B
    target.addEventListener("load", listener, {once: true}); // 処理C
  });
};

const uploadImage = Vue.component('upload-image', {

  data() {
    return {
      files: [],
      cdnUrl: ""
    }
  },

  mounted() {
    const clipboard = new Clipboard('.clipboard');

    clipboard.on('success', function (e) {
      console.info('Action:', e.action);
      console.info('Text:', e.text);
      console.info('Trigger:', e.trigger);

      e.clearSelection();
    });
  },
  computed: {},
  methods: {
    inputFile: function (newFile, oldFile) {
      if (newFile && (!oldFile || newFile.file !== oldFile.file)) {
        // Create a blob field
        newFile.blob = ''
        let URL = window.URL || window.webkitURL
        if (URL && URL.createObjectURL) {
          newFile.blob = URL.createObjectURL(newFile.file)
        }
        // Thumbnails
        newFile.thumb = ''
        if (newFile.blob && newFile.type.substr(0, 6) === 'image/') {
          newFile.thumb = newFile.blob
        }
      }
    },
    inputFilter: function (newFile, oldFile, prevent) {
      if (newFile && !oldFile) {
        // Filter non-image file
        if (!/\.(jpeg|jpe|jpg|gif|png|webp)$/i.test(newFile.name)) {
          return prevent()
        }
      }

      // Create a blob field
      newFile.blob = ''
      let URL = window.URL || window.webkitURL
      if (URL && URL.createObjectURL) {
        newFile.blob = URL.createObjectURL(newFile.file)
      }
    },
    upload: async function () {

      const reader = new FileReader();
      reader.readAsDataURL(this.files[0].file);
      await awaitForLoad(reader);

      const base64 = reader.result.replace(new RegExp("^data:image/.*;base64,"), "");
      const res = await axios.post("/front/upload", {
        label: "none",
        image: base64
      });

      this.cdnUrl = res.data.url;
    }
  },
  template: `
    <div class="contents">      
      <div class="input-group mb-3">
      
        <div class="input-group-prepend">
          <file-upload
            class="form-control btn btn-outline-secondary"
            ref="upload"
            v-model="files"
            :multiple="false"
            :drop="true"
            :drop-directory="true"
            post-action="/post.method"
            put-action="/put.method"
            @input-file="inputFile"
            @input-filter="inputFilter">1. Select image
          </file-upload>
        </div>
      
        <div class="input-group-prepend">
          <span class="input-group-text">→</span>
        </div>
      
        <div class="input-group-prepend">
          <button class="btn btn-primary" type="button" id="button-addon1" @click="upload" :disabled="files.length == 0">2. Upload</button>
        </div>
        
        <div class="input-group-prepend">
          <span class="input-group-text">→</span>
        </div>
        
        <div class="input-group-prepend">
          <button class="btn btn-info clipboard" type="button" :data-clipboard-text="cdnUrl" :disabled="cdnUrl == '' ">3. Copy URL</button>
        </div>
        
        <input
          type="text"
          readonly
          class="form-control"
          v-model="cdnUrl"
          placeholder=""
          aria-label="Example text with button addon"
          aria-describedby="button-addon">
      </div>
      
      <template v-if="files.length">
        <div v-for="(file, index) in files" :key="file.id">
          <img v-if="file.thumb" :src="file.thumb"/>
          <span v-else>No Image</span>
        </div>
      </template>
      
    </div>
  `
});
