const listImage = Vue.component('list-image', {

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
    clickCallback: async function (pageNum) {
      await this.refreshData(Math.max(Number(pageNum) - 1, 0));
    },
    refreshData: async function (pageNumber) {
      const params = {params: {page: pageNumber, size: 10}};

      const response = await axios.get('/manager/list', params);

      this.page = response.data;
    },
    deleteItem: async function (id) {
      await axios.delete(`/manager/${id}/delete`);
      await this.refreshData(0);
    }
  },
  template: `
    <div class="contents">  
    
      <div>
        <table class="table table-layout-fixed">
          <thead class="thead-light">
          <tr>
            <th>ID</th>
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
              <button class="btn btn-danger" @click="deleteItem(item.id)">DELETE</button>
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
