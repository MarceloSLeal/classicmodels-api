import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { DataGrid, GridToolbarContainer, GridActionsCellItem } from "@mui/x-data-grid";
import {
  Box, useTheme, Dialog, DialogActions, DialogContent, DialogTitle,
  DialogContentText
} from "@mui/material";
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import AddIcon from '@mui/icons-material/Add';
import EditIcon from '@mui/icons-material/Edit';
import Button from '@mui/material/Button';


import Header from "../../components/Header";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import { tokens } from "../../theme";
//remover mockDataEmployees
import { mockDataEmployees } from "../../data/mockDataEmployees";

const Employees = () => {

  const urlData = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(urlData.employee.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);
  const [idDelete, setIdDelete] = useState(null);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();
  const navigate = useNavigate();

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  const EditToolBar = () => {
    const handleClick = () => {
      navigate("/formaddemployee", { state: { data } })
    }
  }

  const columns = [
    { field: "id", headerName: "ID", flex: 0.5 },
    { field: "lastName", headerName: "LAST NAME", flex: 1, cellClassName: "name-column--cell" },
    { field: "firstName", headerName: "FIRST NAME", flex: 1, cellClassName: "name-column--cell" },
    { field: "email", headerName: "EMAIL", flex: 1 },
    { field: "reportsTo", headerName: "REPORTS TO", flex: 1 },
    { field: "jobTitle", headerName: "JOB TITLE", flex: 1 },
    { field: "extension", headerName: "EXTENSION", flex: 1 },
    { field: "officesId", headerName: "OFFICE ID", flex: 1 },
    { field: "customersId", headerName: "CUSTOMERS ID", flex: 1 },

  ];

  return (
    <Box m="20px">
      <Header title="EMPLOYEES" subtitle="List of Employees" />
      <Box
        m="40px 0 0 0"
        height="75vh"
        sx={{
          "& .MuiDataGrid-root": {
            border: "none",
          },
          "& .MuiDataGrid-cell": {
            borderBottom: "none",
          },
          "& .name-column--cell": {
            color: colors.greenAccent[300],
          },
          "& .MuiDataGrid-columnHeader": {
            backgroundColor: colors.blueAccent[700],
            borderBottom: "none",
            fontSize: 14
          },
          "& .MuiDataGrid-virtualScroller": {
            backgroundColor: colors.primary[400],
          },
          "& .MuiDataGrid-footerContainer": {
            borderTop: "none",
            backgroundColor: colors.blueAccent[700],
          },
          "& .MuiDataGrid-toolbarContainer .MuiButton-text": {
            color: `${colors.grey[100]} !important`,
          },
        }}
      >
        <DataGrid
          rows={mockDataEmployees}
          columns={columns}
        />
      </Box>
    </Box>
  )
}

export default Employees;
