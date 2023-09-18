<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用例ID(test_case）" prop="testCaseId">
        <el-input
          v-model="queryParams.testCaseId"
          placeholder="请输入用例ID(test_case）"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="步骤名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入步骤名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预期结果" prop="expectResult">
        <el-input
          v-model="queryParams.expectResult"
          placeholder="请输入预期结果"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="测试结果" prop="testResult">
        <el-input
          v-model="queryParams.testResult"
          placeholder="请输入测试结果"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="实际情况" prop="actualSituation">
        <el-input
          v-model="queryParams.actualSituation"
          placeholder="请输入实际情况"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['step:step:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['step:step:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['step:step:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['step:step:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stepList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="用例测试步骤ID" align="center" prop="id" />
      <el-table-column label="用例ID(test_case）" align="center" prop="testCaseId" />
      <el-table-column label="步骤名称" align="center" prop="name" />
      <el-table-column label="预期结果" align="center" prop="expectResult" />
      <el-table-column label="测试结果" align="center" prop="testResult" />
      <el-table-column label="实际情况" align="center" prop="actualSituation" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['step:step:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['step:step:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改用例步骤对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用例ID(test_case）" prop="testCaseId">
          <el-input v-model="form.testCaseId" placeholder="请输入用例ID(test_case）" />
        </el-form-item>
        <el-form-item label="步骤名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入步骤名称" />
        </el-form-item>
        <el-form-item label="预期结果" prop="expectResult">
          <el-input v-model="form.expectResult" placeholder="请输入预期结果" />
        </el-form-item>
        <el-form-item label="测试结果" prop="testResult">
          <el-input v-model="form.testResult" placeholder="请输入测试结果" />
        </el-form-item>
        <el-form-item label="实际情况" prop="actualSituation">
          <el-input v-model="form.actualSituation" placeholder="请输入实际情况" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listStep, getStep, delStep, addStep, updateStep } from "@/api/step/step";

export default {
  name: "Step",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 用例步骤表格数据
      stepList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        testCaseId: null,
        name: null,
        expectResult: null,
        testResult: null,
        actualSituation: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        testCaseId: [
          { required: true, message: "用例ID(test_case）不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询用例步骤列表 */
    getList() {
      this.loading = true;
      listStep(this.queryParams).then(response => {
        this.stepList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        testCaseId: null,
        name: null,
        expectResult: null,
        testResult: null,
        actualSituation: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加用例步骤";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getStep(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改用例步骤";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStep(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStep(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除用例步骤编号为"' + ids + '"的数据项？').then(function() {
        return delStep(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('step/step/export', {
        ...this.queryParams
      }, `step_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
