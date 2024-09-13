import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { GridActionsCellItem } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import EditIcon from '@mui/icons-material/Edit';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from "../../components/Header";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import { tokens } from "../../theme";
import ConfirmDeleteDialog from '../../components/dialogs/ConfirmDeleteDialog';
import BoxDataGrid from '../../components/boxes/BoxDataGrid';

const Employees = () => {

  const urlData = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(urlData.employees.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);
  const [idDelete, setIdDelete] = useState(null);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();

  const handleEditDatagridButton = (params) => () => {
    const rowData = params.row;
    navigateEdit("/formeditemployee", { state: { rowData } });
  };
  const handleDeleteDatagridButton = (params) => () => {
    setIdDelete(params.id);

    setDialogConfirmOpen(true);
  }
  const handleCloseConfirm = () => {
    setDialogConfirmOpen(false);
  }

  const handleDeleteConfirm = async () => {
    setDialogConfirmOpen(false);
    const urlDelete = Urls(idDelete);

    setStatus('');

    try {
      const response = await fetch(urlDelete.employees.findById_Put_Delete, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      setStatus(response.status);

      if (response.status === 204) {
        setStatus(`${response.status} Employee Deleted Succesfully!`);

        refreshData();

      } else {
        setStatus(`Error: ${response.status} Failed to delete Employee`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message} Failed to delete Employee`);
    }

    setDialogDeleteOpen(true);
  }

  const handleCloseDelete = () => {
    setDialogDeleteOpen(false);
  }

  const refreshData = async () => {
    try {
      const response = await fetch(urlData.employees.findAll_Post);
      const updatedData = await response.json();

      setRows(updatedData);
    } catch (error) {
      // TODO -- verificar como colocar isso em um dialog
      console.error('Failed to refresh data:', error);
    }
  };

  const GridActionTooltip = styled(({ className, ...props }) => (
    <Tooltip {...props} classes={{ popper: className }} />
  ))(() => ({
    [`& .${tooltipClasses.tooltip}`]: {
      backgroundColor: colors.primary[400],
      color: colors.primary[100],
      fontSize: 15,
      border: `1px solid ${colors.primary[100]}`,
    },
  }));

  const columns = [
    { field: "id", headerName: "ID", flex: 0.5 },
    { field: "lastName", headerName: "LAST NAME", flex: 1, cellClassName: "name-column--cell" },
    { field: "firstName", headerName: "FIRST NAME", flex: 1, cellClassName: "name-column--cell" },
    { field: "email", headerName: "EMAIL", flex: 1 },
    { field: "reportsTo", headerName: "REPORTS TO", flex: 1 },
    { field: "jobTitle", headerName: "JOB TITLE", flex: 1 },
    { field: "extension", headerName: "EXTENSION", flex: 1 },
    { field: "officeId", headerName: "OFFICE ID", flex: 1 },
    { field: "customersId", headerName: "CUSTOMERS ID", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [

          <GridActionTooltip title="Edit this Employee"
            placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,

          <GridActionTooltip title="Delete this Employee"
            placement="bottom">
            <GridActionsCellItem
              icon={<DeleteIcon />}
              label="Delete"
              onClick={handleDeleteDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,
        ];
      },
    },
  ];

  useEffect(() => {
    if (data) {
      setRows(data);
    }
  }, [data]);

  if (loading) {
    return (
      <Box m="20px">
        <Header title="EMPLOYEES" subtitle="Managing Employees" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="CUSTOMERS" subtitle="Managing Employees" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="EMPLOYEES" subtitle="Manage Employees" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddemployee"}
      />

      <ConfirmDeleteDialog
        dialogConfirmOpen={dialogConfirmOpen} handleCloseConfirm={handleCloseConfirm}
        contextText={"ID:"} contextVar={idDelete} handleDeleteConfirm={handleDeleteConfirm}
        dialogDeleteOpen={dialogDeleteOpen} handleCloseDelete={handleCloseDelete}
        status={status}
      />

    </Box>
  )
}

export default Employees;
