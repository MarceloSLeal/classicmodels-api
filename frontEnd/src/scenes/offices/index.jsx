import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

import { DataGrid, GridToolbarContainer, GridActionsCellItem } from "@mui/x-data-grid";
import {
  Box, useTheme, Dialog, DialogActions, DialogContent, DialogTitle,
  DialogContentText
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/DeleteOutlined";
import AddIcon from "@mui/icons-material/Add";
import EditIcon from "@mui/icons-material/Edit";
import Button from "@mui/material/Button";
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from '../../components/Header';
import useFetchData from '../../api/getData';
import { Urls } from '../../api/Paths';
import { tokens } from "../../theme";

const Offices = () => {

  const url = Urls();
  const theme = useTheme();
  const colors = tokens(theme.palette.mode);
  const { data, loading, error } = useFetchData(url.offices.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
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
      navigate("/formaddoffices")
    }

    return (
      <GridToolbarContainer>
        <Button
          sx={{
            backgroundColor: colors.blueAccent[700],
            color: colors.primary[100],
            paddingTop: '10px', paddingRight: '10px',
            paddingBottom: '10px', paddingLeft: '10px',
          }}
          startIcon={<AddIcon />} onClick={handleClick}>
          Add record
        </Button>
      </GridToolbarContainer>
    );
  }

  const handleEditDatagridButton = (params) => () => {
    const rowData = params.row;
    navigateEdit("/formeditoffices", { state: { rowData } });
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
      const response = await fetch(urlDelete.offices.findById_Put_Delete, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      setStatus(response.status);

      if (response.status === 204) {
        setStatus(`${response.status} Office Deleted Succesfully!`);

        setRows((prevData) => prevData.filter((item) => item.id !== idDelete));
      } else {
        setStatus(`Error: ${response.status} Failed to delete Office`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message} Failed to delete Office`);
    }

    setDialogDeleteOpen(true);
  }
  const handleCloseDelete = () => {
    setDialogDeleteOpen(false);
  }

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
    { field: "city", headerName: "CITY", flex: 1, cellClassName: "name-column--cell" },
    { field: "country", headerName: "COUNTRY", flex: 1, cellClassName: "name-column--cell" },
    { field: "state", headerName: "STATE", flex: 1 },
    { field: "phone", headerName: "PHONE", flex: 1 },
    { field: "addressLine1", headerName: "ADRESS1", flex: 1 },
    { field: "addressLine2", headerName: "ADRESS2", flex: 1 },
    { field: "postalCode", headerName: "POSTAL CODE", flex: 1 },
    { field: "territory", headerName: "TERRITORY", flex: 1 },
    { field: "employeeId", headerName: "EMPLOYEES ID", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [
          <GridActionTooltip title="Edit this Office"
            placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,

          <GridActionTooltip title="Delete this Office"
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

  if (loading) {
    return (
      <Box m="20px">
        <Header title="OFFICES" subtitle="Manage Offices" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>
    )
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="OFFICES" subtitle="Manage Offices" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="OFFICES" subtitle="Manage Offices" />
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
          rows={rows}
          columns={columns}
          slots={{
            toolbar: EditToolBar,
          }}
        />
      </Box>

      <Dialog open={dialogConfirmOpen} onClose={handleCloseConfirm}>
        <DialogTitle>CONFIRM DELETE?</DialogTitle>
        <DialogContent>
          <DialogContentText>
            ID: {idDelete}
          </DialogContentText>
          <DialogActions>
            <Button onClick={handleDeleteConfirm} color="error">
              DELETE
            </Button>
            <Button onClick={handleCloseConfirm} color="inherit">
              CANCEL
            </Button>
          </DialogActions>
        </DialogContent>
      </Dialog>

      <Dialog open={dialogDeleteOpen} onClose={handleCloseDelete}>
        <DialogTitle>CUSTOMER DELETED</DialogTitle>
        <DialogContent>
          <DialogContentText>
            ID: {idDelete}
            <br />
            Status: {status}
          </DialogContentText>
          <DialogActions>
            <Button onClick={handleCloseDelete} color="inherit">
              OK
            </Button>
          </DialogActions>
        </DialogContent>
      </Dialog>

    </Box>
  )
}

export default Offices;
