const imageList = Vue.component('imageList', {

  data() {
    return {
      page: {
        totalPages: 0
      }
    }
  },

  mounted() {
    this.refreshData(0);
  },

  computed: {
    getPageCount: function () {
      return this.page.totalPages;
    },
    getFormatDateTime: function (unixtime) {
      return function (unixtime) {
        return moment.unix(unixtime).utc().format("YYYY/MM/DD HH:mm");
      }
    }
  },

  methods: {
    clickCallback: function (pageNum) {
      this.refreshData(Math.max(Number(pageNum) - 1, 0));
    },
    refreshData: function (pageNumber) {
      const params = {params: {page: pageNumber, size: IMAGE_LIST_ENTRY_SIZE}};

      axios.get(IMAGE_LIST_URL, params)
           .then((res) => {
             this.page = res.data;
           })
           .catch((res) => {
             console.error(res);
           });
    }
  },
  template: `
    <div class="contents">  
    
      <div>
        <table class="table table-layout-fixed">
          <thead class="thead-light">
          <tr>
            <th>ID</th>
            <th>label</th>
            <th>thumbnail</th>
            <th>timestamp</th>
            <th>action</th>
          </tr>
          </thead>
          <tbody v-cloak>
          <tr
            v-for="item in page.content"
            :key="item.id"
            :class="{ 'table-warning': !item.active, 'table-info': item.active }">
            <td>{{ item.id }}</td>
            <td>{{ item.label }}</td>
            <td>
              <img :src="item.thumbnailUrl">
            </td>
            <td>
              {{ getFormatDateTime(item.timestamp) }}
            </td>
            <td>
              <template v-if='item.active'>
                <button class="btn btn-warning">DEACTIVE</button>
              </template>
              <template v-else>
                <button class="btn btn-info">ACTIVE</button>
              </template>
              <button class="btn btn-danger">DELETE</button>
            </td>
          </tr>
          </tbody>
        </table>
        
        <paginate
          :page-count="getPageCount"
          :page-range="3"
          :margin-pages="2"
          :click-handler="clickCallback"
          :prev-text="'<'"
          :next-text="'>'"
          :page-link-class="'page-link'"
          :prev-link-class="'page-link'"
          :next-link-class="'page-link'"
          :container-class="'pagination'"
          :first-button-text="'<<'"
          :last-button-text="'>>'"
          :first-last-button="true"
          :page-class="'page-item'">
        </paginate>
        
      </div>
    </div>
  `
});
