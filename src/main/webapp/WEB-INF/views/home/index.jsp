
<div class="col-sm-9">
<!-- Page -->
  <div class="page animsition">
    <div class="page-content container-fluid">
      <!-- Panel line css animation Chart -->
      <div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">CSS ANIMATION</h3>
        </div>
        <div class="panel-body">
          <!-- Example css animation Chart -->
          <div class="example-wrap">
            <div class="row row-lg">
              <div class="col-lg-9">
                <div class="example">
                  <div class="ct-chart ct-golden-section" id="exampleLineAnimation"></div>
                </div>
              </div>
              <div class="col-lg-3">
                <p class="margin-top-20">Specifying the style of your chart in CSS is not only cleaner but
                  also enables you to use awesome CSS animations and transitions
                  to be applied to your SVG elements!</p>
                  
                  <div style="text-align: center; padding-top: 10px">
      	
			        <a class="btn btn-sm btn-default btn-outline btn-round" href="https://gionkunz.github.io/chartist-js/" target="_blank">
			          <i class="icon wb-link"></i> <span>Chartist Official Website</span>
			        </a>
			      </div>
              </div>
              
            </div>
          </div>
          <!-- End Example css animation Chart -->
        </div>
      </div>
      <!-- End Panel Pie -->
      <!-- Panel Line -->
      <div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Line</h3>
        </div>
        <div class="panel-body">
          <div class="row row-lg">
            <div class="col-md-6">
              <!-- Example Simple Line Chart -->
              <div class="example-wrap">
                <h4 class="example-title">Simple Line Chart</h4>
                <p>An example of a simple line chart with three series.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleSimpleLine"></div>
                </div>
              </div>
              <!-- End Example Simple Line Chart -->
            </div>

            <div class="col-md-6">
              <!-- Example Line Scatter Diagram -->
              <div class="example-wrap">
                <h4 class="example-title">Line Scatter Diagram</h4>
                <p>This advanced example uses a line chart to draw a scatter diagram.
                  The data object is created with a functional style random mechanism.
                  There is a mobile first responsive configuration using the responsive
                  options to show less labels on small screens.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleLineScatter"></div>
                </div>
              </div>
              <!-- End Example Line Scatter Diagram -->
            </div>

            <div class="col-md-6">
              <!-- Example Bi-Polar Line -->
              <div class="example-wrap">
                <h4 class="example-title">Bi-Polar Line Chart With Area Only</h4>
                <p>You can also only draw the area shapes of the line chart. Area
                  shapes will always be constructed around their areaBase (that
                  can be configured in the options) which also allows you to draw
                  nice bi-polar areas.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleOnlyArea"></div>
                </div>
              </div>
              <!-- End Example Bi Polar Line -->
            </div>

            <div class="col-md-6">
              <!-- Example Advanced Smil Animations -->
              <div class="example-wrap">
                <h4 class="example-title">Advanced Smil Animations</h4>
                <p>Chartist provides a simple API to animate the elements on the Chart
                  using SMIL. Usually you can achieve most animation with CSS3
                  animations but in some cases you'd like to animate SVG properties
                  that are not available in CSS.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleLineAnimations"></div>
                </div>
              </div>
              <!-- End Example Advanced Smil Animations -->
            </div>

            <div class="col-md-6">
              <!-- Example Svg Path Animation -->
              <div class="example-wrap margin-md-0">
                <h4 class="example-title">Svg Path Animation</h4>
                <p>Path animation is made easy with the SVG Path API. The API allows
                  you to modify complex SVG paths and transform them for different
                  animation morphing states.</p>
                <div class="example">
                  <div class="ct-chart" id="examplePathAnimation"></div>
                </div>
              </div>
              <!-- Example Svg Path Animation -->
            </div>

            <div class="col-md-6">
              <!-- Example Line Interpolation -->
              <div class="example-wrap">
                <h4 class="example-title">Line Interpolation / Smoothing</h4>
                <p>By default Chartist uses a cardinal spline algorithm to smooth
                  the lines. However, like all other things in Chartist, this can
                  be customized easily! </p>
                <div class="example">
                  <div class="ct-chart" id="exampleSmoothingLine"></div>
                </div>
              </div>
              <!-- End Example Line Interpolation -->
            </div>
          </div>
        </div>
      </div>
      <!-- End Panel Line -->

      <!-- Panel Bar -->
      <div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Bar</h3>
        </div>
        <div class="panel-body">
          <div class="row row-lg">
            <div class="col-md-6">
              <!-- Example Bi-Polar Bar -->
              <div class="example-wrap">
                <h4 class="example-title">Bi-Polar Bar Chart</h4>
                <p>A bi-plar bar chart with a range limit set with low and high. There
                  is also an interpolation function used to skip every odd grid
                  line / label.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleBiPolarBar"></div>
                </div>
              </div>
              <!-- End Example Bi Polar Bar -->
            </div>

            <div class="col-md-6">
              <!-- Example Overlapping Bars -->
              <div class="example-wrap">
                <h4 class="example-title">Overlapping Bars On Mobile</h4>
                <p>This example makes use of label interpolation and the seriesBarDistance
                  property that allows you to make bars overlap over each other.
                  This then can be used to use the available space on mobile better.
                  </p>
                <div class="example">
                  <div class="ct-chart" id="exampleOverlappingBar"></div>
                </div>
              </div>
              <!-- End Example Overlapping Bars -->
            </div>

            <div class="col-md-6">
              <!-- Example Stacked Bar Chart -->
              <div class="example-wrap">
                <h4 class="example-title">Stacked Bar Chart</h4>
                <p>You can also set your bar chart to stack the series bars on top
                  of each other easily by using the stackBars property in your
                  configuration.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleStackedBar"></div>
                </div>
              </div>
              <!-- End Example Stacked Bar Chart -->
            </div>

            <div class="col-md-6">
              <!-- Example Horizontal Bar -->
              <div class="example-wrap margin-md-0">
                <h4 class="example-title">Horizontal Bar Chart</h4>
                <p>Guess what! Creating horizontal bar charts is as simple as it can
                  get. There's no new chart type you need to learn, just passing
                  an additional option is enough.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleHorizontalBar"></div>
                </div>
              </div>
              <!-- End Example Horizontal Bar -->
            </div>
          </div>
        </div>
      </div>
      <!-- End Panel Bar -->

      <!-- Panel Pie -->
      <div class="panel">
        <div class="panel-heading">
          <h3 class="panel-title">Pie</h3>
        </div>
        <div class="panel-body">
          <div class="row row-lg">
            <div class="col-md-6">
              <!-- Example Simple Pie Chart -->
              <div class="example-wrap">
                <h4 class="example-title">Simple Pie Chart</h4>
                <p>A very simple pie chart with label interpolation to show percentage
                  instead of the actual data series value.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleSimplePie"></div>
                </div>
              </div>
              <!-- End Example Simple Pie Chart -->
            </div>

            <div class="col-md-6">
              <!-- Example Gauge Chart -->
              <div class="example-wrap">
                <h4 class="example-title">Gauge Chart</h4>
                <p>This pie chart uses donut, startAngle and total to draw a gauge
                  chart.</p>
                <div class="example">
                  <div class="ct-chart" id="exampleGaugePie"></div>
                </div>
              </div>
              <!-- End Example Gauge Chart -->
            </div>
          </div>
        </div>
      </div>
      <!-- End Panel Pie -->
    </div>
  </div>
  <!-- End Page -->
  </div>
  
  <script src="assets/js/chartist.js"></script>
<script src="assets/js/chartist-data.js"></script>