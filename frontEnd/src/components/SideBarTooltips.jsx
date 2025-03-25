import Tooltip from '@mui/material/Tooltip';

const CustomTooltip = ({ title, isCollapsed, X, Y, children }) => {
  return isCollapsed ? (
    <Tooltip
      title={title}
      componentsProps={{
        tooltip: { sx: { fontSize: "14px" } }
      }}
      slotProps={{
        popper: {
          modifiers: [{
            name: 'offset',
            options: {
              offset: [X, Y],
            }
          }]
        }
      }}
    >
      <div style={{ alignItems: "center" }}>
        {children}
      </div>
    </Tooltip>
  ) : (
    <>{children}</>
  );

};

export default CustomTooltip;
