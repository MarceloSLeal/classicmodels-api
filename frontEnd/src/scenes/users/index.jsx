import React, { useState, useEffect } from 'react';

import { GridActionsCellItem } from "@mui/x-data-grid";
import { Box, useTheme } from "@mui/material";
import DeleteIcon from '@mui/icons-material/DeleteOutlined';
import Tooltip, { tooltipClasses } from '@mui/material/Tooltip';
import { styled } from '@mui/material/styles';

import Header from "../../components/Header";
import useFetchData from "../../api/getData";
import { Urls } from "../../api/Paths";
import { tokens } from "../../theme";
import ConfirmDeleteDialog from "../../components/dialogs/ConfirmDeleteDialog"
import BoxDataGrid from "../../components/boxes/BoxDataGrid"
import DeleteScenes from '../../components/formsRequests/DeleteScenes';

const Users = () => {
  const urlData = Urls();
  const theme = useTheme();
  const { data, loading, error } = useFetchData(urlData.users.findAll);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);

  const [idDelete, setIdDelete] = useState(null);
  const [emailDelete, setEmailDelete] = useState(null);
  const colors = tokens(theme.palette.mode);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);

  const handleDeleteDatagridButton = (params) => () => {
    setIdDelete(params.id);
    setEmailDelete(params.row.email);

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

      const response = await DeleteScenes(urlDelete.users.delete);

      setStatus(response.status);

      if (response.status === 204) {
        setStatus(`${response.status} User Deleted Succesfully!`);

        setRows((prevData) => prevData.filter((item) => item.id !== idDelete));
      } else {
        setStatus(`Error: ${response.status} Failed to delete User`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message} Failed to delete User`);
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
    { field: "email", headerName: "EMAIL", flex: 1 },
    { field: "role", headerName: "ROLE", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [

          <GridActionTooltip title="Delete this User"
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
        <Header title="USERS" subtitle="Manage Users" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="Users" subtitle="Manage Users" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="USERS" subtitle="Manage Users" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formadduser"}
      />

      <ConfirmDeleteDialog
        dialogConfirmOpen={dialogConfirmOpen} handleCloseConfirm={handleCloseConfirm}
        contextText={"EMAIL:"} contextVar={emailDelete} handleDeleteConfirm={handleDeleteConfirm}
        dialogDeleteOpen={dialogDeleteOpen} handleCloseDelete={handleCloseDelete}
        status={status}
      />

    </Box>
  )

}

export default Users;
