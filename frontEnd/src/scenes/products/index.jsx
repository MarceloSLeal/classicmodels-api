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

const Products = () => {
  const urlData = Urls();
  const theme = useTheme();
  const { data, loading, error } = useFetchData(urlData.products.findAll_Post);
  const [dialogConfirmOpen, setDialogConfirmOpen] = useState(false);
  const [dialogDeleteOpen, setDialogDeleteOpen] = useState(false);

  const [idDelete, setIdDelete] = useState(null);
  const colors = tokens(theme.palette.mode);
  const [status, setStatus] = useState('');
  const [rows, setRows] = useState([]);
  const navigateEdit = useNavigate();

  const handleEditDatagridButton = (params) => () => {
    const rowData = params.row;
    navigateEdit("/formeditproducts", { state: { rowData } });
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
      const response = await fetch(urlDelete.products.put_Delete, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      setStatus(response.status);

      if (response.status === 204) {
        setStatus(`${response.status} Product Deleted Succesfully!`);

        setRows((prevData) => prevData.filter((item) => item.id !== idDelete));
      } else {
        setStatus(`Error: ${response.status} Failed to delete Product`);
      }
    } catch (error) {
      setStatus(`Error: ${error.message} Failed to delete Product`);
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
    { field: "productLine", headerName: "PRODUCT LINE", flex: 1 },
    { field: "scale", headerName: "SCALE", flex: 1 },
    { field: "vendor", headerName: "VENDOR", flex: 1 },
    { field: "description", headerName: "DESCRIPTION", flex: 1 },
    { field: "quantityInStock", headerName: "QUANTITY", flex: 1 },
    { field: "buyPrice", headerName: "BUY PRICE", flex: 1 },
    { field: "msrp", headerName: "MSRP", flex: 1 },

    {
      field: 'actions',
      type: 'actions',
      headerName: 'ACTIONS',
      width: 100,
      cellClassName: 'actions',
      getActions: (params) => {

        return [

          <GridActionTooltip title="Edit this Product"
            placement="bottom">
            <GridActionsCellItem
              icon={<EditIcon />}
              label="Edit"
              className="textPrimary"
              onClick={handleEditDatagridButton(params)}
              color="inherit"
            />
          </GridActionTooltip>,

          <GridActionTooltip title="Delete this Product"
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
        <Header title="PRODUCTS" subtitle="Manage Products" />
        <Box
          sx={{ fontSize: "2rem" }} >
          Loading...
        </Box>
      </Box>)
  }

  if (error) {
    return (
      <Box m="20px">
        <Header title="PRODUCTS" subtitle="Manage Products" />
        <Box>Error: {error.message}{ }</Box>
      </Box>
    );
  }

  return (
    <Box m="20px">
      <Header title="PRODUCTS" subtitle="Manage Products" />

      <BoxDataGrid
        colors={colors}
        rows={rows}
        columns={columns}
        path={"/formaddproducts"}
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

export default Products;
