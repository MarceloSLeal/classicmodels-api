import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";

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
import ConfirmDeleteDialog from "../../components/dialogs/ConfirmDeleteDialog"
import BoxDataGrid from "../../components/boxes/BoxDataGrid"
import DeleteScenes from '../../components/formsRequests/DeleteScenes';

const Customers = () => {
  const urlData = Urls();
  const theme = useTheme();
  const { data, loading, error } = useFetchData(urlData.customers.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);

  const [idDelete, setIdDelete] = useState(null);
  const colors = tokens(theme.palette.mode);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();

  const handleEditDatagridButton = (params) => () => {
    const rowData = params.row;
    navigateEdit("/formeditcustomer", { state: { rowData } });
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

      const response = DeleteScenes(urlDelete.customers.findById_Put_Delete);

      setStatus(response.status);

      if (response.status === 204) {
        setStatus(`${response.status} Customer Deleted Succesfully!`);

        setRows((prevData) => prevData.filter((item) => item.id !== idDelete));
      } else {
        setStatus(`Error: ${response.status} Failed to delete Customer`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message} Failed to delete Customer`);
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
    { field: "name", headerName: "NAME", flex: 1, cellClassName: "name-column--cell" },
    { field: "email", headerName: "EMAIL", flex: 1 },
    { field: "contactLastName", headerName: "CONT.LAST NAME", flex: 1 },
    { field: "contactFirstName", headerName: "CONT.FIRST NAME", flex: 1 },
    { field: "phone", headerName: "PHONE", flex: 1 },
    { field: "addressLine1", headerName: "ADRESS1", flex: 1 },
    { field: "addressLine2", headerName: "ADRESS2", flex: 1 },
    { field: "city", headerName: "CITY", flex: 1 },
    { field: "state", headerName: "STATE", flex: 1 },
    { field: "postalCode", headerName: "P.CODE", flex: 1 },
    { field: "country", headerName: "COUNTRY", flex: 1 },
    { field: "creditLimit", headerName: "CREDIT", flex: 1 },
    { field: "employeeId", headerName: "EMP.ID", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [

          <GridActionTooltip title="Edit this Customer"
            placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,

          <GridActionTooltip title="Delete this Customer"
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
        <Header title="CUSTOMERS" subtitle="Manage Customers" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="CUSTOMERS" subtitle="Manage Customers" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="CUSTOMERS" subtitle="Manage Customers" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddcustomer"}
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

export default Customers;
