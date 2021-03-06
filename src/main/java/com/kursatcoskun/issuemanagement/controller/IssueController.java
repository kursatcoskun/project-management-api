package com.kursatcoskun.issuemanagement.controller;

import com.kursatcoskun.issuemanagement.dto.IssueDetailDto;
import com.kursatcoskun.issuemanagement.dto.IssueDto;
import com.kursatcoskun.issuemanagement.dto.IssueInputDto;
import com.kursatcoskun.issuemanagement.entities.IssueStatus;
import com.kursatcoskun.issuemanagement.services.impl.IssueServiceImpl;
import com.kursatcoskun.issuemanagement.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(ApiPaths.IssueCtrl.CTRL)
@Api(value = ApiPaths.IssueCtrl.CTRL, description = "Issue APIs")
@CrossOrigin
public class IssueController {

    private final IssueServiceImpl issueServiceImpl;

    public IssueController(IssueServiceImpl issueServiceImpl) {
        this.issueServiceImpl = issueServiceImpl;
    }

    @GetMapping("/getAllByPagination")
    @ApiOperation(value = "Get By Pagination Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<TPage<IssueDto>>> getAllPageable(Pageable pageable) {
        TPage<IssueDto> data = issueServiceImpl.getAllPageable(pageable);
        return ResponseEntity.ok(new UtilResponse<TPage<IssueDto>>(data, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @GetMapping("/getIssueById/{id}")
    @ApiOperation(value = "Get By Id Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<IssueDto>> getById(@PathVariable("id") Long id) {
        IssueDto issueDto = issueServiceImpl.getById(id);
        return ResponseEntity.ok(new UtilResponse<IssueDto>(issueDto, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @GetMapping("/getIssueByIdWithDetails/{id}")
    @ApiOperation(value = "Get Issue By Id With Details Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<IssueDetailDto>> getIssueByIdWithDetails(@PathVariable(value = "id", required = true) Long id) {
        IssueDetailDto detailDto = issueServiceImpl.getByIdWithDetails(id);
        return ResponseEntity.ok(new UtilResponse<IssueDetailDto>(detailDto, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @PostMapping("/CreateIssue")
    @ApiOperation(value = "Create Issue Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<IssueDto>> createIssue(@Valid @RequestBody IssueInputDto issueInputDto) {
        IssueDto issueSaved = issueServiceImpl.save(issueInputDto);
        return ResponseEntity.ok(new UtilResponse<IssueDto>(issueSaved, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @PutMapping("/updateIssue/{id}")
    @ApiOperation(value = "Update Issue Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<IssueDetailDto>> updateProject(@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody IssueInputDto issueInputDto) {
        IssueDetailDto issueUpdated = issueServiceImpl.update(id, issueInputDto);
        return ResponseEntity.ok(new UtilResponse<IssueDetailDto>(issueUpdated, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @DeleteMapping("/deleteIssue/{id}")
    @ApiOperation(value = "Delete Issue Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<IssueDto>> deleteProject(@PathVariable(value = "id", required = true) Long id) {
        if (!issueServiceImpl.delete(id)) {
            return ResponseEntity.badRequest().body(new UtilResponse<IssueDto>(null, new ProcessResult("400", ResponseMessage.ERROR)));
        }
        return ResponseEntity.ok((new UtilResponse<IssueDto>(null, new ProcessResult("200", ResponseMessage.SUCCESS))));
    }

    @GetMapping("/statuses")
    @ApiOperation(value = "Get All Issue Statuses Operation", response = String.class, responseContainer = "List")
    public ResponseEntity<UtilResponse<List<IssueStatus>>> getIssueStatuses() {
        List<IssueStatus> data = Arrays.asList(IssueStatus.values());
        return ResponseEntity.ok(new UtilResponse<List<IssueStatus>>(data, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @GetMapping("/GetIssuesByProjectId/{id}")
    @ApiOperation(value = "Get Issues By Project ID Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<TPage<IssueDto>>> getIssuesByProjectId(@PathVariable(value = "id", required = true) Long id, Pageable pageable) {
        TPage<IssueDto> issues = issueServiceImpl.getIssuesByProjectId(id, pageable);
        return ResponseEntity.ok(new UtilResponse<TPage<IssueDto>>(issues, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @GetMapping("/GetIssuesByAssigneeAndStatus/{id}/{issueStatus}")
    @ApiOperation(value = "Get Issues By Assignee ID and Issue Status Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<TPage<IssueDto>>> getIssuesByProjectId(@PathVariable(value = "id", required = true) Long id, @PathVariable(value = "issueStatus", required = true) IssueStatus issueStatus, Pageable pageable) {
        TPage<IssueDto> issues = issueServiceImpl.getIssuesByAssigneeAndIssueStatus(id, issueStatus, pageable);
        return ResponseEntity.ok(new UtilResponse<TPage<IssueDto>>(issues, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @GetMapping("/dashboardIssue/{id}/{issueStatus}")
    @ApiOperation(value = "Get Issues By Assignee ID and Issue Status Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<List<IssueDto>>> getDashboardIssue(@PathVariable(value = "id", required = true) Long id, @PathVariable(value = "issueStatus", required = true) IssueStatus issueStatus, Pageable pageable) {
        List<IssueDto> issues = issueServiceImpl.getAllIssuesByAssigneeAndIssueStatus(id, issueStatus);
        return ResponseEntity.ok(new UtilResponse<List<IssueDto>>(issues, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }

    @GetMapping("/getAllIssuesByAssignee/{id}")
    @ApiOperation(value = "Get Issues By Assignee ID and Issue Status Operation", response = UtilResponse.class)
    public ResponseEntity<UtilResponse<List<IssueDto>>> getAllIssuesByAssignee(@PathVariable(value = "id", required = true) Long id) {
        List<IssueDto> issues = issueServiceImpl.getAllIssuesByAssignee(id);
        return ResponseEntity.ok(new UtilResponse<List<IssueDto>>(issues, new ProcessResult("200", ResponseMessage.SUCCESS)));
    }
}
